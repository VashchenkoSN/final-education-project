package ru.sberbank.jd.tgbot.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sberbank.jd.tgbot.service.UserCheckService;
import ru.sberbank.jd.tgbot.service.TelegramBotService;

/**
 * Фоновое задание по проверке пользователей группы.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CheckUsersJob {

    private final UserCheckService userCheckService;
    private final TelegramBotService telegramBotService;

    /**
     * Фоновое задание по проверке пользователей группы.
     */
    @Scheduled(cron = "${job.cron}")
    @Async
    public void checkUser() {
        log.info("Start verification users of group");

        userCheckService.checkAndDeleteChatMembers();

    }

}
