package com.example.mypet.controller;


import com.example.mypet.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final PetRepository petRepository;


    @GetMapping("/mypet/v1")
    public String HelloWorld(){

        return "Hello World";
    }
    @GetMapping("/")
    public String HelloWorld2(){
        System.out.println("hello world2");
        petRepository.findAll().forEach(System.out::println);
        return "Hello World!!!!";
    }

    @GetMapping("/auth")
    public String HelloWorld3(){
        return "test!";
    }
}
