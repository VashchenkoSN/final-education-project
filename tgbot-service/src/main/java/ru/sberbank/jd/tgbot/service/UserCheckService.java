package ru.sberbank.jd.tgbot.service;

import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.tgbot.entity.User;
import ru.sberbank.jd.tgbot.exchange.EmployeeServiceClient;

/**
 * Сервис для проверки участников группы.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserCheckService {

    private final UserService userService;
    private final EmployeeServiceClient employeeServiceClient;
    private final TelegramBotService telegramBotService;

    /**
     * Верификация пользователей группы.
     */
    public void checkAndDeleteChatMembers() {

        List<User> userList = userService.findAll();

        try {
            List<EmployeeDto> employeeList = employeeServiceClient.getEmployeeList();

            userList.forEach(user -> checkCharMember(user, employeeList));

        } catch (FeignException exception) {
            log.info("Error when getting employee list - {}", exception.getMessage());
        }

    }

    /**
     * Проверить является ли участник группы  сотрудником компании.
     *
     * @param user         -участник группы (пользователь)
     * @param employeeList - список сотрудников
     */
    private void checkCharMember(User user, List<EmployeeDto> employeeList) {
        if (employeeList.stream()
                .noneMatch(employeeDto -> employeeDto.getLogin().equals(user.getLogin()))) {
            log.info("User with login {} is not employee. Рe will be removed from the group", user.getLogin());
            telegramBotService.deleteFromCompanyChat(user);
        }
    }
}
