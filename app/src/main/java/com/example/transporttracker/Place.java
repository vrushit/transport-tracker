package com.example.transporttracker;

public class Place {
    private String Name, Address, PhoneNo;

    public Place(String Name, String Address, String PhoneNo) {
        this.Name = Name;
        this.Address = Address;
        this.PhoneNo = PhoneNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = Address;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public Place(){

    }
}
