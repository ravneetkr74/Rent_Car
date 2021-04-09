package com.example.rentcar.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Booking {
    String bookingID;
    String carID;
    String userID;
    String from;
    String to;
    String pickup;
    String drop;
    String price;


    public Booking(){

    }

    public Booking(String bookingID, String carID, String userID, String from, String to, String pickup, String drop, String price) {
        this.bookingID = bookingID;
        this.carID = carID;
        this.userID = userID;
        this.from = from;
        this.to = to;
        this.pickup = pickup;
        this.drop = drop;
        this.price = price;
    }
}
