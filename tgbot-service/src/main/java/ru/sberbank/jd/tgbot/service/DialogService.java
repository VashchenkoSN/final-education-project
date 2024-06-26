package ru.sberbank.jd.tgbot.service;

import static java.util.Objects.isNull;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.tgbot.config.BotConfig;
import ru.sberbank.jd.tgbot.entity.DialogEntity;
import ru.sberbank.jd.tgbot.entity.DialogState;
import ru.sberbank.jd.tgbot.entity.User;
import ru.sberbank.jd.tgbot.exchange.EmployeeServiceClient;

/**
 * Сервис, отвечающий за диалог бота с пользователем.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DialogService {

    private final HashMap<Long, DialogEntity> storage = new HashMap<Long, DialogEntity>();
    private final EmployeeServiceClient employeeServiceClient;
    private final BotConfig botConfig;
    private final UserService userService;

    private static final String GET_LOGIN_BOT_QUESTION = "Приветствую, %s. Введите свой логин в нашей компании";
    private static final String GET_LOGIN_BOT_GOOD_ANSWER = "Ваш логин %s найден среди сотрудников компании. Вам доступна регистрация в группе %s";
    private static final String GET_LOGIN_BOT_NEGATIVE_ANSWER = "Ваш логин %s не обнаружен среди сотрудников компании. Доступ в группу закрыт";
    private static final String UNDEFINE_BOT_ANSWER = "Даже не знаю что ответить...";

    /**
     * Вернуть диалог пользователя.
     *
     * @param chatId - номер чата.
     * @return - данные диалога.
     */
    public DialogEntity get(Long chatId) {
        DialogEntity dialogEntity = storage.get(chatId);
        if (isNull(dialogEntity)) {
            dialogEntity = init(chatId, "");
        }
        return dialogEntity;
    }

    /**
     * Инициализация диалога с пользователем.
     *
     * @param chatId - chatId
     * @return - данные диалога
     */
    public DialogEntity init(Long chatId, String name) {
        DialogEntity dialogEntity = new DialogEntity();
        dialogEntity.setChatId(chatId);
        dialogEntity.setDialogState(DialogState.GET_LOGIN);
        dialogEntity.setLastBotMessage(String.format(GET_LOGIN_BOT_QUESTION, name));
        storage.put(chatId, dialogEntity);
        return dialogEntity;
    }

    /**
     * Обработать ответ пользователя.
     *
     * @param chatId  - chatId
     * @param message - сообщение
     * @return - данные диалога
     */
    public String processMessage(Long chatId, String message) {
        DialogEntity dialogEntity = get(chatId);
        switch (dialogEntity.getDialogState()) {
            case GET_LOGIN -> {
                dialogEntity = processGetLogin(chatId, message);
                return dialogEntity.getLastBotMessage();
            }
            case FINISHED -> {
                dialogEntity = processFinished(chatId, message);
                return dialogEntity.getLastBotMessage();
            }
            default -> {
                return UNDEFINE_BOT_ANSWER;
            }
        }
    }

    /**
     * Обработать ответ пользователя на запрос логина.
     *
     * @param chatId  - chatId
     * @param message - сообщение пользователя
     * @return - данные диалога
     */
    public DialogEntity processGetLogin(Long chatId, String message) {
        DialogEntity dialogEntity = storage.get(chatId);
        dialogEntity.setLastUserMessage(message);

        String botAnswer;
        if (isEmployee(message)) {
            log.info("User with login {} is employee", message);
            User user = new User(message, chatId);
            userService.addUser(user);
            botAnswer = String.format(GET_LOGIN_BOT_GOOD_ANSWER, message, botConfig.getInviteLink());
            dialogEntity.setDialogState(DialogState.FINISHED);
        } else {
            log.info("User with login {} is not employee", message);
            botAnswer = String.format(GET_LOGIN_BOT_NEGATIVE_ANSWER, message);
            dialogEntity.setDialogState(DialogState.GET_LOGIN);
        }
        dialogEntity.setLastBotMessage(botAnswer);
        return dialogEntity;
    }

    private DialogEntity processFinished(Long chatId, String message) {
        return get(chatId);
    }

    /**
     * Проверить, что пользователь является сотрудником компании.
     *
     * @param login -логин пользователя
     * @return - true или false
     */
    private boolean isEmployee(String login) {
        EmployeeDto employeeDto = null;
        log.info("Try get information in employee-service by login {}", login);
        try {
            employeeDto = employeeServiceClient.getEmployeeByLogin(login);
        } catch (FeignException exception) {
            log.info(exception.getMessage());
        }

        return !isNull(employeeDto);
    }

}
