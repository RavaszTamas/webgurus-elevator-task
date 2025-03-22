package com.tamas.ravasz.learning.elevatorbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableScheduling
public class ElevatorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElevatorBackendApplication.class, args);
    }

}
