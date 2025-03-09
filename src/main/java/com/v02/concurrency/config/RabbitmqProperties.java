package com.v02.concurrency.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.main.rabbitmq")
@Getter
@Setter
public class RabbitmqProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
