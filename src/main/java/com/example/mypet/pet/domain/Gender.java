package com.example.mypet.pet.domain;

public enum Gender {

    MALE("수컷"),
    FEMALE("암컷"),
    NOT_SURE("잘 모르겠어요");

    private final String description;


   Gender(String description){
       this.description = description;
   }

   public String getDescription(){
       return description;
   }
}
