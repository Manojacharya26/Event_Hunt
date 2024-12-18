package com.example.eventhunt;

public class VenueDataClass {
    private String dataTitle;

    private String dataDesc;
    private String dataEntry;
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

    public String getDataEntry() {
        return dataEntry;
    }


    public String getDataDesc() {
        return dataDesc;
    }


    public String getDataImage() {
        return dataImage;
    }

    public VenueDataClass(String dataTitle, String dataEntry,  String dataDesc, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataEntry = dataEntry;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
    }
    public VenueDataClass(){

    }
}

