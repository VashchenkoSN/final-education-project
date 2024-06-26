package ru.sberbank.jd.tgbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sberbank.jd.tgbot.config.BotConfig;


/**
 * Управляющий класс телеграмм-бота.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final DialogService dialogService;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    processUserMessage(chatId, messageText);
            }
        }
    }

    /**
     * Стартовое приветствие бота.
     *
     * @param chatId    - id чата.
     * @param firstName - имя пользователя из телеграмма.
     */
    private void startCommandReceived(long chatId, String firstName) {
        dialogService.init(chatId, firstName);
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
    private void processUserMessage(long chatId, String message) {
        log.info("ChatId {} - User send message '{}'", chatId, message);
        String botAnswer = dialogService.processMessage(chatId, message);
        sendMessage(chatId, botAnswer);
    }

}
