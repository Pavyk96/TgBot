package com.pavyk96.TgBot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final List<CommandHandler> commandHandlers;
    private final List<CallbackHandler> callbackHandlers;

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            commandHandlers.stream()
                    .filter(handler -> handler.canHandle(command))
                    .findFirst()
                    .ifPresent(handler -> handler.handle(update));
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            callbackHandlers.stream()
                    .filter(handler -> handler.canHandle(callbackData))
                    .findFirst()
                    .ifPresent(handler -> handler.handle(update));
        }
    }
}
