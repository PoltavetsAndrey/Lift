package com.poltavets;

import java.util.ArrayList;
import java.util.List;

public class Main {

    final int minNumOfFloors = 5;
    final int maxNumOfFloors = 20;
    final int minNumOfPeoplePerFloor = 0;
    final int maxNumOfPeoplePerFloor = 10;
    final int elevatorCapacity = 5;
    List<Floor> floors = new ArrayList<>();

    public static void main(String[] args) {
        Main main = new Main();
        main.notStaticMain();
    }

    private void notStaticMain() {
        builderHouse();
        ElevatorController controller = new ElevatorController();
        controller.dispetcher(floors, elevatorCapacity);
    }

    // Create house
    private void builderHouse() {
        final int numberOfFloors = random(minNumOfFloors, maxNumOfFloors);
        // Create floors
        for (int itemOfFloor = 1; itemOfFloor <= numberOfFloors; itemOfFloor++) {
            Floor floor = new Floor();
            List<Passenger> passengers = new ArrayList<>();
            floor.setItemOfFloor(itemOfFloor);
            floor.setNumberOfArrived(0);
            int numOfPeoplePerFloor = random(minNumOfPeoplePerFloor, maxNumOfPeoplePerFloor);
            // Create passengers
            for (int itemOfPassenger = 1; itemOfPassenger <= numOfPeoplePerFloor; itemOfPassenger++) {
                Passenger passenger = new Passenger();
                int target = random(minNumOfFloors, numberOfFloors);
                // Checking if the target is different from the current floor
                if (target == itemOfFloor) {
                    if (itemOfFloor != numberOfFloors) {
                        target++;
                    } else {
                        target = 1;
                    }
                }
                passenger.setTarget(target);
                passengers.add(passenger);
            }
            floor.setWaitingPeople(passengers);
            floors.add(floor);
        }
    }

    private int random(int min, int max) {
        return (int) (Math.random() * (max + 1 - min)) + min;
    }
}
