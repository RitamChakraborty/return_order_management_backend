package dev.ritam.component_processing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ComponentProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComponentProcessingApplication.class, args);
    }
}
