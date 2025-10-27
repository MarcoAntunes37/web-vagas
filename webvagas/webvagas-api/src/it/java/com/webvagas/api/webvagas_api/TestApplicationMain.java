package com.webvagas.api.webvagas_api;

import org.springframework.boot.SpringApplication;

import com.webvagas.api.webvagas_api.config.ContainersConfig;

public class TestApplicationMain {
    public static void main(String[] args) {
        SpringApplication
                .from(WebvagasApiApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}