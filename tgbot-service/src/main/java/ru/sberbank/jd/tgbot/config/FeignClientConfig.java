package ru.sberbank.jd.tgbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.stereotype.Component;

/**
 * Конфигурация FeignClient.
 */
@Component
@Configuration
@Setter
@Getter
public class FeignClientConfig {

    @Value("${feign.username}")
    private String username;
    @Value("${feign.password}")
    private String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
}

