package com.example.finalyearproject.data;

import com.google.firebase.database.Exclude;

import lombok.NoArgsConstructor;

public class Car {
    private String carId;
    private String carName;
    private String ownerName;
    private String price;
    private String carStatus;

    public Car(String carName, String ownerName, String price, String carStatus) {
        this.carName = carName;
        this.ownerName = ownerName;
        this.price = price;
        this.carStatus = carStatus;
    }

    public Car(){}

    public String getCarName() {
        return carName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPrice() {
        return price;
    }

    public String getCarStatus() {
        return carStatus;
    }

    @Exclude
    public String getCarId(){
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
