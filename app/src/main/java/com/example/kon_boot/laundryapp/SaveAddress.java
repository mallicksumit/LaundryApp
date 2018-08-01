package com.example.kon_boot.laundryapp;

public class SaveAddress {
    String home, houseno, landmark, address, city, area, subarea;

    public SaveAddress() {
    }

    public SaveAddress(String home, String houseno, String landmark, String address, String city, String area, String subarea) {
        this.home = home;
        this.houseno = houseno;
        this.landmark = landmark;
        this.address = address;
        this.city = city;
        this.area = area;
        this.subarea = subarea;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSubarea() {
        return subarea;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }
}