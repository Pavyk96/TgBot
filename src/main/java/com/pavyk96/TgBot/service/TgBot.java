package com.pavyk96.TgBot.service;

import com.pavyk96.TgBot.config.BotConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TgBot extends TelegramLongPollingBot {

    @NonNull
    private final BotConfig botConfig;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String massageText = update.getMessage().getText();
            Chat chat = update.getMessage().getChat();
            long chatId = update.getMessage().getChatId();

            switch (massageText) {
                case "/start":
                    startMassage(chatId, chat);
                    break;
                default:
                    sendMassage(chatId, "Прости, но я не знаю такой команды(");
            }

        }
    }

    private void startMassage(long chatId, Chat chat) {
        String answer = "Привет, " + chat.getUserName() + ". Надеюсь я смогу тебе помочь!";
        sendMassage(chatId, answer);
    }

    private void sendMassage(long chatId, String massage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(massage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
