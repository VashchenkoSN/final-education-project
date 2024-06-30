package ru.sberbank.jd.tgbot.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.jd.tgbot.entity.User;
import ru.sberbank.jd.tgbot.repository.UserRepository;

/**
 * Сервисный класс для User.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    /**
     * Добавить пользователя в БД.
     *
     * @return - данные пользователя
     */
    public User addUser(User user) {
        log.info("User with login {} was saved in DB", user.getLogin());
        return repository.save(user);
    }

    /**
     * Поиск пользователя по логину.
     *
     * @param login - логин
     * @return - пользователь
     */
    public User findUserByLogin(String login) {
        return repository.findByLogin(login).orElse(null);
    }

    /**
     * Вернуть список всех участников группы.
     *
     * @return - список участников группы
     */
    public List<User> findAll() {
        return repository.findAll();
    }

}
