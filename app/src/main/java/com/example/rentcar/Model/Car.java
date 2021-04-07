package com.example.rentcar.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Car {

    public String name;
    public String description;
    public String price;
    public String mileage;
    public String fuel;
    public String seats;
    public String id;

    public Car() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Car(String name, String description, String price, String mileage, String fuel, String seats, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.mileage = mileage;
        this.fuel = fuel;
        this.seats = seats;
        this.id = id;
    }
}
