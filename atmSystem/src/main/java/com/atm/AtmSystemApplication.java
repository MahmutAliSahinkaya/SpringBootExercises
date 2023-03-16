package com.atm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Atm Project API", version = "0.1", description = "Atm Project API Service"))
public class AtmSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmSystemApplication.class, args);
    }

}
