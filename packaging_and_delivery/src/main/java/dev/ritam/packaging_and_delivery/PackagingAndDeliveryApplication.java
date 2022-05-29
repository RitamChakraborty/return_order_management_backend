package dev.ritam.packaging_and_delivery;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@OpenAPIDefinition
@SpringBootApplication
@EnableEurekaClient
public class PackagingAndDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(PackagingAndDeliveryApplication.class, args);
    }
}