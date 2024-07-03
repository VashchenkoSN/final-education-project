package ru.sberbank.jd.tgbot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sberbank.jd.tgbot.repository.ChatMemberRepository;

/**
 * Тесты для CharMemberService
 */

@SpringBootTest
class ChatMemberServiceTest {

    @MockBean
    private ChatMemberRepository repository;
    @Autowired
    private ChatMemberService service;

    /**
     * Тест сохранения в БД пользователей, вступивших в группу.
     */
    @Test
    void processNewChatMembers() {
        User user = new User();
        user.setId(1L);
        service.processNewChatMembers(List.of(user));
        Mockito.verify(repository, times(1)).saveAllAndFlush(any());
    }

    /**
     * Тест удаления из БД пользователей, вступивших в группу.
     */
    @Test
    void processLeftChatMembers() {
        service.processLeftChatMembers(1L);
        Mockito.verify(repository, times(1)).deleteById(1L);
    }

    /**
     * Тест методо по возврату всех участников группы.
     */
    @Test
    void getAll() {
        service.getAll();
        Mockito.verify(repository, times(1)).findAll();
    }
}