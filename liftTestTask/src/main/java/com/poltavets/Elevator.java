package com.poltavets;

import java.util.List;

public class Elevator {

    private String direction;
    private List<Passenger> elevatorPassengers;
    private int currentFloor;

    public Elevator() {
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Passenger> getElevatorPassengers() {
        return elevatorPassengers;
    }

    public void setElevatorPassengers(List<Passenger> elevatorPassengers) {
        this.elevatorPassengers = elevatorPassengers;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
