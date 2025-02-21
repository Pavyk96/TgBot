package com.pavyk96.TgBot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private TelegramLongPollingBot bot;

    public void setBot(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка при отправке сообщения: " + e.getMessage(), e);
        }
    }
}
