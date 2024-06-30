package ru.sberbank.jd.tgbot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Сущность уведомление.
 */
@Getter
@Setter
public class Notification {

    private Long chatId;
    private String textToSend;
}
