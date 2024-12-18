package com.example.eventhunt;


public class BookEventDataClass {

    private String Title;
    private String Loc;
    private String Time;

    private String Date;
    private String Entry;
    private String Ticket;

    private String Total;
    private String key;
    public String getKey() {
        return key;
    }

    public void setTitle(String title) {
        Title = title;
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

    public void setEntry(String entry) {
        Entry = entry;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getTitle() {
        return Title;
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

    public String getEntry() {
        return Entry;
    }

    public String getTicket() {
        return Ticket;
    }

    public String getTotal() {
        return Total;
    }


    public BookEventDataClass(String title, String loc, String time, String date, String entry, String ticket, String total) {
        Title = title;
        Loc = loc;
        Time = time;
        Date = date;
        Entry = entry;
        Ticket = ticket;
        Total = total;
    }

    public BookEventDataClass(){

    }
}