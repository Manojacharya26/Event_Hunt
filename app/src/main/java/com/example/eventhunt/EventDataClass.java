package com.example.eventhunt;


public class EventDataClass {

    private String dataTitle;
    private String dataLoc;
    private String dataTime;

    private String dataDate;
    private String dataEntry;
    private String dataDesc;

    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataLoc() {
        return dataLoc;
    }

    public String getDataDate() {
        return dataDate;
    }
    public String getDataTime() {
        return dataTime;
    }
    public String getDataEntry() {
        return dataEntry;
    }
    public String getDataDesc() {
        return dataDesc;
    }


    public String getDataImage() {
        return dataImage;
    }

    public EventDataClass(String dataTitle, String dataLoc, String dataDate, String dataTime, String dataEntry, String dataDesc, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataLoc = dataLoc;
        this.dataDate = dataDate;
        this.dataTime = dataTime;
        this.dataEntry = dataEntry;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
    }
    public EventDataClass(){

    }
}