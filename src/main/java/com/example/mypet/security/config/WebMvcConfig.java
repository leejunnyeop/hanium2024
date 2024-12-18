//package com.example.mypet.security.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    //허용 시간
//    private final long MAX_AGE_SECS = 3600;
//
//
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("https://happy-maru.net","http://localhost:8080", "*")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")    //허용되는 Method
//                .allowedHeaders("*")    //허용되는 헤더
//                .allowCredentials(true)    //자격증명 허용
//                .maxAge(MAX_AGE_SECS);
//    }
//}