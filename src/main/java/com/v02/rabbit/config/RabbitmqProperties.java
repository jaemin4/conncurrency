package com.v02.rabbit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.rabbit.rabbitmq")
@Getter
@Setter
public class RabbitmqProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
