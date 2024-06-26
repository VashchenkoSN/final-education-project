package ru.sberbank.jd.tgbot.config;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Класс-reader настроечных параметров.
 */
@Component
@ConfigurationProperties(prefix = "bot")
@Setter
@Getter
public class BotConfig {

   private String name;
   private String token;
   private String inviteLink;

}
