package com.pavyk96.TgBot.utils;

import com.pavyk96.TgBot.handler.CommandHandler;
import com.pavyk96.TgBot.handler.CallbackHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class CommandDispatcher {

    private final List<CommandHandler> commandHandlers;
    private final List<CallbackHandler> callbackHandlers;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void dispatch(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            executorService.submit(() -> {
                for (CommandHandler handler : commandHandlers) {
                    if (handler.canHandle(command)) {
                        handler.handle(update);
                        return;
                    }
                }
            });
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            executorService.submit(() -> {
                for (CallbackHandler handler : callbackHandlers) {
                    if (handler.canHandle(callbackData)) {
                        handler.handle(update);
                        return;
                    }
                }
            });
        }
    }
}
