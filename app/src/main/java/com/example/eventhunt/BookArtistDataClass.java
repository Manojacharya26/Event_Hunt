package com.example.eventhunt;

public class BookArtistDataClass {
    private String Name;
    private String Loc;
    private String Time;

    private String Date;
    private String Fee;
    private String Duration;

    private String Total;
    private String key;
    public String getKey() {
        return key;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return Name;
    }

    public String getLoc() {
        return Loc;
    }

    public String getTime() {
        return Time;
    }

    public String getDate() {
        return Date;
    }

    public String getFee() {
        return Fee;
    }

    public String getDuration() {
        return Duration;
    }

    public String getTotal() {
        return Total;
    }

    public BookArtistDataClass(String name, String loc, String time, String date, String fee, String duration, String total) {
        Name = name;
        Loc = loc;
        Time = time;
        Date = date;
        Fee = fee;
        Duration = duration;
        Total = total;
    }
public BookArtistDataClass(){

}
}
