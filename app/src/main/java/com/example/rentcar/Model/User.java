package com.example.rentcar.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String id;
    public String name;
    public String email;
    public String address;
    public String city;
    public String phone;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String id, String name, String email, String address, String city, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }
}
