package ru.sberbank.jd.tgbot.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sberbank.jd.tgbot.entity.User;

/**
 * Конвертер сообщений.
 */
@Component
public class UserConverter {

    /**
     * Получить пользователя из данных сообщения.
     *
     * @param user - user
     * @return - сущность User
     */
    public User convertChatToUser(org.telegram.telegrambots.meta.api.objects.User user, Chat chat) {
        User userEntity = new User();
        userEntity.setChatId(chat.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setUsername(user.getUserName());
        return userEntity;
    }

}
