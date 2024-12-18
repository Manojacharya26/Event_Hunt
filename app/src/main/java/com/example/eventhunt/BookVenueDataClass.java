package com.example.eventhunt;

public class BookVenueDataClass {
    private String Name;
    private String Occasion;
    private String Time;

    private String Date;
    private String Fee;
    private String Duration;

    private String Total;
    private String key;

    public BookVenueDataClass(String name, String occasion, String time, String date, String fee, String duration, String total) {
        Name = name; 
        Occasion = occasion;
        Time = time;
        Date = date;
        Fee = fee;
        Duration = duration;
        Total = total;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOccasion() {
        return Occasion;
    }

    public void setOccasion(String occasion) {
        Occasion = occasion;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getKey() {
        return key;
    } 

    public void setKey(String key) {
        this.key = key;
    }

    public  BookVenueDataClass(){

    }
}
