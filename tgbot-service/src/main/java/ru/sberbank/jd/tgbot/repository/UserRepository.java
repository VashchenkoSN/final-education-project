package ru.sberbank.jd.tgbot.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.jd.tgbot.entity.User;

/**
 * Репозиторий для User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String string);

}
