package com.tamas.ravasz.learning.lift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LiftBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(LiftBackendApplication.class, args);
  }
}
