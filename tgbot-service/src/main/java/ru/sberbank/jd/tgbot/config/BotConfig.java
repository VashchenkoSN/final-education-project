package ru.sberbank.jd.tgbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Класс-reader настроечных параметров телеграмм-бота.
 */
@Component
@ConfigurationProperties(prefix = "bot")
@Setter
@Getter
public class BotConfig {

    private String name;
    private String token;
    private String inviteLink;
    private String companyChatId;
}
