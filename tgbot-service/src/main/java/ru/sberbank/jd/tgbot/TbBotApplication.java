package ru.sberbank.jd.tgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Основной класс.
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class TbBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbBotApplication.class, args);
    }

}
