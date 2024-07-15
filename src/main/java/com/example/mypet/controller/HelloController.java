package com.example.mypet.controller;


import com.example.mypet.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class HelloController {

    private final PetRepository petRepository;


    @GetMapping("/mypet/v1")
    public String HelloWorld(){
        log.info("hello world");
        log.warn("hello world");
        log.fatal("hello world");
        log.error("hello world");
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
