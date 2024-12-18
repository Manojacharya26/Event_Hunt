package com.example.eventhunt;

public class AdminClass {
   private String email;
   private String password;

   public AdminClass(){

   }
    public AdminClass(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
