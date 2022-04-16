package dev.ritam.component_processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ComponentProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComponentProcessingApplication.class, args);
    }
}
