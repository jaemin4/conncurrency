package com.v02.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("rabbit")
@SpringBootApplication
public class RabbitApplication {
    public static void main(String[] args) throws InterruptedException{
        SpringApplication.run(RabbitApplication.class, args);
    }

}
