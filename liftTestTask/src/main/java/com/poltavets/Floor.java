package com.poltavets;

import java.util.List;

public class Floor {

    private int itemOfFloor;
    private List<Passenger> waitingPeople;
    private int numberOfArrived;

    public Floor() {
    }

    public int getItemOfFloor() {
        return itemOfFloor;
    }

    public void setItemOfFloor(int itemOfFloor) {
        this.itemOfFloor = itemOfFloor;
    }

    public List<Passenger> getWaitingPeople() {
        return waitingPeople;
    }

    public void setWaitingPeople(List<Passenger> waitingPeople) {
        this.waitingPeople = waitingPeople;
    }

    public int getNumberOfArrived() {
        return numberOfArrived;
    }

    public void setNumberOfArrived(int numberOfArrived) {
        this.numberOfArrived = numberOfArrived;
    }
}
