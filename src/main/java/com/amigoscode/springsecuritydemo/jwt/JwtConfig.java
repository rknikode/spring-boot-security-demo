package com.amigoscode.springsecuritydemo.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.jwt")
public class JwtConfig {
    public String secretKey;
    public String tokenPrefix;
    public Long tokenExpirationAfterDay;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Long getTokenExpirationAfterDay() {
        return tokenExpirationAfterDay;
    }

    public void setTokenExpirationAfterDay(Long tokenExpirationAfterDay) {
        this.tokenExpirationAfterDay = tokenExpirationAfterDay;
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
