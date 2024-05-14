package com.example.mypet.controller;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HelloController {
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
        return "Hello World2";
    }
}
