package com.example.transporttracker_new;

public class Place {
    private String Name, Address, PhoneNo, Longitude, Latitude;

    public Place(String Name, String Address, String PhoneNo, String Longitude, String Latitude) {
        this.Name = Name;
        this.Address = Address;
        this.PhoneNo = PhoneNo;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
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
