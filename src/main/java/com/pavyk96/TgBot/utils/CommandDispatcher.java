package com.pavyk96.TgBot.utils;

import com.pavyk96.TgBot.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandDispatcher {

    private final List<CommandHandler> commandHandlers;

    public void dispatch(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            for (CommandHandler handler : commandHandlers) {
                if (handler.canHandle(command)) {
                    handler.handle(update);
                    return;
                }
            }
        }
    }
}
