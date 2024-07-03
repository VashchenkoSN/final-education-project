package ru.sberbank.jd.tgbot.service;

import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.tgbot.entity.ChatMember;
import ru.sberbank.jd.tgbot.entity.User;
import ru.sberbank.jd.tgbot.exchange.EmployeeServiceClient;

/**
 * Сервис для проверки участников группы.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMembersCheckService {

    private final UserService userService;
    private final EmployeeServiceClient employeeServiceClient;
    private final ChatMemberService chatMemberService;
    private final TelegramBotService telegramBotService;

    /**
     * Верификация участников группы.
     */
    public void checkAndDeleteChatMembers() {

        List<ChatMember> chatMemberList = chatMemberService.getAll();
        List<User> userList = userService.findAll();

        try {
            List<EmployeeDto> employeeList = employeeServiceClient.getEmployeeList();

            chatMemberList.forEach(chatMember -> checkChatMember(chatMember, employeeList, userList));

        } catch (FeignException exception) {
            log.info("Error when getting employee list - {}", exception.getMessage());
        }

    }

    /**
     * Проверить является ли участник группы  сотрудником компании.
     *
     * @param chatMember   - участник группы (пользователь)
     * @param employeeList - список сотрудников
     */
    private void checkChatMember(ChatMember chatMember, List<EmployeeDto> employeeList, List<User> userList) {

        User user = userList.stream()
                .filter(user1 -> user1.getId().equals(chatMember.getId()))
                .findAny().orElse(null);
        if (user == null) {
            log.info("User with id {} is not employee. User will be removed from the group", chatMember.getId());
            telegramBotService.deleteFromCompanyChat(chatMember.getId());
            return;
        }

        if (employeeList.stream()
                .noneMatch(employeeDto -> employeeDto.getLogin().equals(user.getLogin()))) {
            log.info("User with id {} is not employee. User will be removed from the group", chatMember.getId());
            telegramBotService.deleteFromCompanyChat(chatMember.getId());
        }
    }
}
