package ru.sberbank.jd.tgbot.service;

import static java.util.Objects.isNull;

import feign.FeignException;
import java.util.HashMap;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.tgbot.config.BotConfig;
import ru.sberbank.jd.tgbot.entity.DialogEntity;
import ru.sberbank.jd.tgbot.entity.DialogState;
import ru.sberbank.jd.tgbot.entity.Notification;
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

    private static final String QUESTION_REQUEST_LOGIN = "Приветствую, %s. Введите свой логин в нашей компании";
    private static final String ANSWER_LOGIN_FOUND = "Ваш логин %s найден среди сотрудников компании. Вам доступна регистрация в группе %s";
    private static final String ANSWER_LOGIN_NOT_FOUND = "Ваш логин %s не обнаружен среди сотрудников компании. Доступ в группу закрыт";
    private static final String ANSWER_UNDEFINE_BOT_ANSWER = "Даже не знаю что ответить...";

    private static final String ANSWER_LOGIN_ALREADY_EIXSTS = "Сотрудник c логином %s уже состоит в группе";
    private static final String NOTIFICATION_USER = "Под вашим логином %s  пользователь %s пытались зарегистрироваться в группе компании";

    /**
     * Вернуть диалог пользователя.
     *
     * @param chatId - номер чата.
     * @return - данные диалога.
     */
    public DialogEntity get(Long chatId) {
        return storage.get(chatId);
    }

    /**
     * Инициализация диалога с пользователем.
     *
     * @param chatId - chatId
     * @return - данные диалога
     */
    public DialogEntity init(Long chatId, User user) {
        DialogEntity dialogEntity = new DialogEntity();
        dialogEntity.setUser(user);
        dialogEntity.setChatId(chatId);
        dialogEntity.setDialogState(DialogState.REQUEST_LOGIN);
        dialogEntity.setLastBotMessage(String.format(QUESTION_REQUEST_LOGIN, user.getFirstName()));
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
            case REQUEST_LOGIN -> {
                dialogEntity = processRequestLogin(chatId, message);
                return dialogEntity.getLastBotMessage();
            }
            case FINISHED -> {
                dialogEntity = processFinished(chatId, message);
                return dialogEntity.getLastBotMessage();
            }
            default -> {
                return ANSWER_UNDEFINE_BOT_ANSWER;
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
    public DialogEntity processRequestLogin(Long chatId, String message) {
        String login = message;
        DialogEntity dialogEntity = storage.get(chatId);
        dialogEntity.setLastUserMessage(login);

        String botAnswer;
        EmployeeDto employeeDto = getEmployeeByLogin(login);
        if (isEmployee(employeeDto)) {
            if (isLoginAlreadyExists(employeeDto, dialogEntity)) {
                log.info("Employee with login {} is already registered in group", login);
                dialogEntity.setDialogState(DialogState.REQUEST_LOGIN);
                botAnswer = String.format(ANSWER_LOGIN_ALREADY_EIXSTS, login);

                fillNotification(dialogEntity, userService.findUserByLogin(login));

            } else {
                log.info("User with login {} is employee", login);
                dialogEntity.getUser().setLogin(login);
                userService.addUser(dialogEntity.getUser());
                botAnswer = String.format(ANSWER_LOGIN_FOUND, login, botConfig.getInviteLink());
                dialogEntity.setDialogState(DialogState.FINISHED);
            }
        } else {
            log.info("User with login {} is not employee", login);
            botAnswer = String.format(ANSWER_LOGIN_NOT_FOUND, login);
            dialogEntity.setDialogState(DialogState.REQUEST_LOGIN);
        }
        dialogEntity.setLastBotMessage(botAnswer);
        return dialogEntity;
    }

    private void fillNotification(DialogEntity dialogEntity, User userToNotify) {

        String textToSend = String.format(NOTIFICATION_USER, userToNotify.getLogin(), dialogEntity.getUser().getUsername());
        Notification notification = new Notification();
        notification.setChatId(userToNotify.getChatId());
        notification.setTextToSend(textToSend);
        dialogEntity.setNotification(notification);
    }

    private DialogEntity processFinished(Long chatId, String message) {
        return get(chatId);
    }

    /**
     * Проверить, что пользователь является сотрудником компании.
     *
     * @param employeeDto - employeeDto
     * @return - true или false
     */
    private boolean isEmployee(EmployeeDto employeeDto) {
        return !isNull(employeeDto);
    }

    /**
     * Получить Данные о пользователе из сервиса Employee-service.
     *
     * @param login - логин
     * @return - данные пользователя
     */
    private EmployeeDto getEmployeeByLogin(String login) {
        EmployeeDto employeeDto = null;
        log.info("Try get information in employee-service by login {}", login);
        try {
            employeeDto = employeeServiceClient.getEmployeeByLogin(login);
        } catch (FeignException exception) {
            log.info(exception.getMessage());
        }
        return employeeDto;
    }

    private boolean isLoginAlreadyExists(EmployeeDto employeeDto, DialogEntity dialogEntity) {
        User existUser = userService.findUserByLogin(employeeDto.getLogin());

        return existUser != null &&
                !Objects.equals(existUser.getChatId(), dialogEntity.getChatId());
    }

}
