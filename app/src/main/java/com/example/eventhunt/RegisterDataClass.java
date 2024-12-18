package com.example.eventhunt;


public class RegisterDataClass {

    private String name;
    private String location;
    private String contact;
    private String email;
    private String idNum;
    private String orgName;

    private String image;
    private String key;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getImage() {
        return image;
    }



    public RegisterDataClass(String name, String location, String contact, String email, String idNum, String orgName, String image) {
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.email = email;
        this.idNum = idNum;
        this.orgName = orgName;
        this.image = image;
    }
    public RegisterDataClass() {
    }

}