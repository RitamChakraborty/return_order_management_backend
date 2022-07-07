package dev.ritam.authorization.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "authorization")
@Data
public class PropertyValuesConfiguration {
    private Map<String, String> jwt;

    public String getSecretKey() {
        return jwt.get("secret-key");
    }
}
