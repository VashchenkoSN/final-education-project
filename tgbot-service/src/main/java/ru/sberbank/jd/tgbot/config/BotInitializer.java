package ru.sberbank.jd.tgbot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.sberbank.jd.tgbot.service.TelegramBotService;


/**
 * Класс инициализации телеграмм-бота.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BotInitializer {

    private final TelegramBotService telegramBotService;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBotService);
        } catch (TelegramApiException telegramApiException) {
            log.info(telegramApiException.getMessage());
        }
    }


}
