package dev.ritam.packaging_and_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PackagingAndDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(PackagingAndDeliveryApplication.class, args);
    }
}