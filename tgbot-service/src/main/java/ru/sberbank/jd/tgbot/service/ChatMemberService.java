package ru.sberbank.jd.tgbot.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sberbank.jd.tgbot.converter.ChatMemberConverter;
import ru.sberbank.jd.tgbot.entity.ChatMember;
import ru.sberbank.jd.tgbot.repository.ChatMemberRepository;

/**
 * Класс описывающий бизнес-логику по добавлению и удалению участников группы.
 */
@Service
@RequiredArgsConstructor
public class ChatMemberService {

    private final ChatMemberRepository repository;
    private final ChatMemberConverter converter;

    /**
     * Сохранить пользователей, вступивших в группу .
     */
    public void processNewChatMembers(List<User> newUsers) {
        List<ChatMember> chatMembers = newUsers.stream()
                .map(converter::convertTgUserToCharMember)
                .toList();

        repository.saveAllAndFlush(chatMembers);
    }


    /**
     * Удалить пользователя, покинувшего группу.
     */
    public void processLeftChatMembers(User leftMember) {

        repository.deleteById(leftMember.getId());

    }

    /**
     * Удалить пользователя, покинувшего группу.
     */
    public void processLeftChatMembers(Long id) {

        repository.deleteById(id);

    }


    /**
     * Возвратить список всех пользователей, состоящий в группе.
     */
    public List<ChatMember> getAll(){
        return repository.findAll();
    }



}
