package ru.sberbank.jd.tgbot.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sberbank.jd.tgbot.entity.User;

/**
 * Конвертер данных пользователя в сущность User.
 */
@Component
public class UserConverter {

    /**
     * Сконвертировать user в User.
     *
     * @param user - user
     * @return - сущность User
     */
    public User convertTgUserToUser(org.telegram.telegrambots.meta.api.objects.User user, Chat chat) {
        User userEntity = new User();
        userEntity.setId(user.getId());
        userEntity.setChatId(chat.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setUsername(user.getUserName());
        return userEntity;
    }

}
