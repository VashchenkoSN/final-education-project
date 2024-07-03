package ru.sberbank.jd.tgbot.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sberbank.jd.tgbot.entity.ChatMember;


/**
 * Конвертер данных пользователя в сущность User.
 */
@Component
public class ChatMemberConverter {

    /**
     * Сконвертировать user в CharMember.
     *
     * @param user - user
     * @return - сущность CharMember
     */
    public ChatMember convertTgUserToCharMember(org.telegram.telegrambots.meta.api.objects.User user) {
        ChatMember chatMember = new ChatMember();
        chatMember.setId(user.getId());
        chatMember.setFirstName(user.getFirstName());
        chatMember.setLastName(user.getLastName());
        chatMember.setUsername(user.getUserName());
        return chatMember;
    }

}
