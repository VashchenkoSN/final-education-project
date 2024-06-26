package ru.sberbank.jd.tgbot.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность диалог(чат).
 */
@Getter
@Setter
public class DialogEntity {
    private Long chatId;
    private DialogState dialogState;
    private String lastUserMessage;
    private String lastBotMessage;
}
