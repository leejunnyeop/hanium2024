package com.example.mypet.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@Log4j2
public class FirebaseConfig {


    @Value("${firebase.key-path}")
    private String firebaseKeyPath;

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        try {
            InputStream serviceAccount = new ClassPathResource(firebaseKeyPath).getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            log.info("fcm setting completed");
            if (FirebaseApp.getApps().isEmpty()){
                return FirebaseApp.initializeApp(options);
            } else {
                return FirebaseApp.getInstance();
            }

        }catch (IOException e){
            log.error(e.getMessage());
            throw new IOException();
        }

    }
}