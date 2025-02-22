package com.pavyk96.TgBot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    boolean canHandle(String callbackData);
    void handle(Update update);
}
