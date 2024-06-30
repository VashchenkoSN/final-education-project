package ru.sberbank.jd.tgbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sberbank.jd.tgbot.config.BotConfig;
import ru.sberbank.jd.tgbot.converter.UserConverter;
import ru.sberbank.jd.tgbot.entity.Notification;
import ru.sberbank.jd.tgbot.entity.User;


/**
 * Управляющий класс телеграмм-бота.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final DialogService dialogService;
    private final UserConverter userConverter;

    /**
     * Возвратить токен бота.
     *
     * @return - токен.
     */
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }


    /**
     * Возвратить имя бота.
     *
     * @return - имя бота
     */
    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }


    /**
     * Обработать сообщение пользователя.
     *
     * @param update - update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() &&
                update.getMessage().getChat().isUserChat()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            User user = userConverter.convertChatToUser(
                    update.getMessage().getFrom(), update.getMessage().getChat());

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, user);
                    break;
                default:
                    processUserMessage(chatId, user, messageText);
            }
        }
    }

    /**
     * Стартовое приветствие бота.
     *
     * @param chatId - id чата.
     * @param user   - пользователь.
     */
    private void startCommandReceived(long chatId, User user) {
        dialogService.init(chatId, user);
        sendMessage(chatId, dialogService.get(chatId).getLastBotMessage());
        log.info("ChatId {} - Bot send message '{}'", chatId, dialogService.get(chatId).getLastBotMessage());
    }


    /**
     * Отправить сообщение пользователю.
     *
     * @param chatId     - id чата
     * @param textToSend - текст сообщения
     */
    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
            log.info("CharId {}, Bot send message '{}'", chatId, message);
        } catch (TelegramApiException exception) {
            log.info(exception.getMessage());
        }
    }

    /**
     * Обработать ответ пользователя.
     *
     * @param chatId   - id чата
     * @param message- текст сообщения
     */
    private void processUserMessage(long chatId, User user, String message) {
        if (dialogService.get(chatId) == null) {
            dialogService.init(chatId, user);
        }
        log.info("ChatId {} - User send message '{}'", chatId, message);
        String botAnswer = dialogService.processMessage(chatId, message);
        sendMessage(chatId, botAnswer);

        //уведомления другим пользователям
        Notification notification = dialogService.get(chatId).getNotification();
        if (notification != null) {
            sendMessage(notification.getChatId(), notification.getTextToSend());
            dialogService.get(chatId).setNotification(null);
        }

    }

    /**
     * Удалить участника группы.
     *
     * @param user - участник
     */
    public void deleteFromCompanyChat(User user) {

        KickChatMember kickChatMember = new KickChatMember(botConfig.getCompanyChatId(), user.getChatId().intValue());
        try {
            execute(kickChatMember);
            log.info("User with login {} was deleted from Company chat", user.getLogin());
        } catch (TelegramApiException exception) {
            log.info(exception.getMessage());
        }

    }

}
