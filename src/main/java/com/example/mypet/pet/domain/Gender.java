package com.example.mypet.pet.domain;

public enum Gender {

    MALE("수컷"),
    FEMALE("암컷");

    private final String description;


   Gender(String description){
       this.description = description;
   }

   public String getDescription(){
       return description;
   }
}
