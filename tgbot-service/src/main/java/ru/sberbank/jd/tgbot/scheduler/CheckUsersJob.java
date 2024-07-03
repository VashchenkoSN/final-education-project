package ru.sberbank.jd.tgbot.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sberbank.jd.tgbot.service.ChatMembersCheckService;

/**
 * Фоновое задание по проверке пользователей группы.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CheckUsersJob {

    private final ChatMembersCheckService chatMembersCheckService;

    /**
     * Фоновое задание по проверке пользователей группы.
     */
    @Scheduled(cron = "${job.cron}")
    @Async
    public void checkUser() {
        log.info("Start verification members of group");

         chatMembersCheckService.checkAndDeleteChatMembers();

    }

}
