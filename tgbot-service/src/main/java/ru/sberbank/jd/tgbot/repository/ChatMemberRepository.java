package ru.sberbank.jd.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.jd.tgbot.entity.ChatMember;

/**
 * Репозиторий для ChatMember.
 */
@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

}
