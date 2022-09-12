package com.poltavets;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {

    public void dispetcher(List<Floor> floors, int capacity) {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(1);
        elevator.setDirection("Up");
        elevator.setElevatorPassengers(new ArrayList<>());
        boolean isNotFinish;
        // Until all the passengers arrive
        do {
            preparationForMovement(floors, elevator, capacity);
            print(floors, elevator);
            move(floors, elevator);
            isNotFinish = false;
            for (Floor floor : floors) {
                // Checking passengers
                if (!floor.getWaitingPeople().isEmpty() ||
                        !elevator.getElevatorPassengers().isEmpty()) {
                    isNotFinish = true;
                    break;
                }
            }
        } while (isNotFinish);
    }

    private void move(List<Floor> floors, Elevator elevator) {
        List<Passenger> waitingPassengers = new ArrayList<>();
        waitingPassengers = floors.get(elevator.getCurrentFloor() - 1).getWaitingPeople();
        // Up Direction Check
        if (elevator.getDirection().equals("Up")) {
            // Checking on the last floor
            if (elevator.getCurrentFloor() < floors.size()) {
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                // Checking for the absence of waiting passengers on the top floor
            } else {
                elevator.setDirection("Down");
                return;
            }

            // If the direction is Down and not 1st floor
        } else if (elevator.getCurrentFloor() != 1) {
            elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
            return;
            // Checking for the absence of waiting passengers on the 1st floor
        } else if (waitingPassengers.size() != 0) {
            elevator.setCurrentFloor(1);
            elevator.setDirection("Up");
            return;
        } else {
            elevator.setDirection("Up");
            elevator.setCurrentFloor(2);
        }
    }

    private void print(List<Floor> floors, Elevator elevator) {
        System.out.println();
        for (int i = floors.size() - 1; i >= 0; i--) {
            Floor floor = floors.get(i);
            if (floor.getItemOfFloor() != elevator.getCurrentFloor()) {
                System.out.print(floor.getItemOfFloor() + " :  " +
                        floor.getNumberOfArrived() + " |                | ");
                for (Passenger passenger : floor.getWaitingPeople()) {
                    System.out.print(passenger.getTarget() + " ");
                }
            } else if (elevator.getDirection().equals("Up")) {
                System.out.print(floor.getItemOfFloor() + " :  " +
                        floor.getNumberOfArrived() + " | ^ ");
                if (!elevator.getElevatorPassengers().isEmpty()) {
                    for (Passenger passenger : elevator.getElevatorPassengers()) {
                        System.out.print(passenger.getTarget() + " ");
                    }
                }
                System.out.print("^ | ");
                for (Passenger passenger : floor.getWaitingPeople()) {
                    System.out.print(passenger.getTarget() + " ");
                }
            } else {
                System.out.print(floor.getItemOfFloor() + " :  " +
                        floor.getNumberOfArrived() + " | v ");
                if (!elevator.getElevatorPassengers().isEmpty()) {
                    for (Passenger passenger : elevator.getElevatorPassengers()) {
                        System.out.print(passenger.getTarget() + " ");
                    }
                }
                System.out.print("v | ");
                for (Passenger passenger : floor.getWaitingPeople()) {
                    System.out.print(passenger.getTarget() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Preparation elevator for movement
    private void preparationForMovement(List<Floor> floors, Elevator elevator, int capacity) {
        // List of passengers in the elevator
        List<Passenger> loaded;
        loaded = elevator.getElevatorPassengers();
        // Loading list
        List<Passenger> landing = new ArrayList<>();
        List<Passenger> arrived = new ArrayList<>();
        Floor floor = floors.get(elevator.getCurrentFloor() - 1);
        List<Passenger> elevatorPassengers;
        if (!elevator.getElevatorPassengers().isEmpty()) {
            elevatorPassengers = elevator.getElevatorPassengers();
            // Unloading
            for (Passenger elevatorPassenger : elevatorPassengers) {
                if (elevatorPassenger.getTarget() == elevator.getCurrentFloor()) {
                    arrived.add(elevatorPassenger);
                    floor.setNumberOfArrived(floor.getNumberOfArrived() + 1);
                }
            }
            elevatorPassengers.removeAll(arrived);
        }
        // Waiting list
        List<Passenger> waitingPassengers = floor.getWaitingPeople();
        if (waitingPassengers.isEmpty()) {
            if (!elevator.getElevatorPassengers().isEmpty()) {
                return;
            } else {
                //Search for nearby passengers
                for (int i = 1; i < floors.size() - 2; i++) {
                    int checkFloor = elevator.getCurrentFloor() - i;
                    // Search below the elevator
                    if (checkFloor >= 1 &&
                            !floors.get(checkFloor - 1).getWaitingPeople().isEmpty()) {
                        elevator.setDirection("Down");
                        break;
                    }
                    checkFloor = elevator.getCurrentFloor() + i;
                    // Search above the elevator
                    if (checkFloor <= floors.size() &&
                            !floors.get(checkFloor - 1).getWaitingPeople().isEmpty()) {
                        elevator.setDirection("Up");
                        break;
                    }
                }
            }

        }
        if (!floor.getWaitingPeople().isEmpty()) {
            if (elevator.getElevatorPassengers().isEmpty()) {
                int numIoDown = 0;
                // Determining the direction of the majority
                for (Passenger waitingPassenger : waitingPassengers) {
                    elevator.setDirection("Up");
                    if (waitingPassenger.getTarget() < elevator.getCurrentFloor()) {
                        numIoDown++;
                        if (numIoDown >= waitingPassengers.size() / 2) {
                            elevator.setDirection("Down");
                        }
                    }
                }
            }
        }
        for (Passenger waitingPassenger : waitingPassengers) {
            // Full load checks
            if ((loaded != null && capacity <= loaded.size())) {
                break;
            }
            if (capacity <= landing.size()) {
                break;
            }
            if (loaded != null && (capacity - landing.size() - loaded.size()) <= 0) {
                break;
            }

            // Search for a travel companion Top
            if (elevator.getDirection().equals("Up") &&
                    waitingPassenger.getTarget() > elevator.getCurrentFloor()) {
                landing.add(waitingPassenger);
                continue;
            }
            // Search for a companion Down
            if (elevator.getDirection().equals("Down") &&
                    waitingPassenger.getTarget() < elevator.getCurrentFloor()) {
                landing.add(waitingPassenger);
            }
        }
        // Reduce the queue on the floor
        waitingPassengers.removeAll(landing);
        // Elevator loading
        if (loaded == null) {
            loaded = landing;
        } else {
            loaded.addAll(landing);
        }
        elevator.setElevatorPassengers(loaded);
    }
}
