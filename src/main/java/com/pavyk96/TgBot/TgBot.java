package com.pavyk96.TgBot;

import com.pavyk96.TgBot.config.BotConfig;
import com.pavyk96.TgBot.utils.CommandDispatcher;
import com.pavyk96.TgBot.utils.MessageSender;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TgBot extends TelegramLongPollingBot {

    @NonNull
    private final BotConfig botConfig;
    @NonNull
    private final CommandDispatcher commandDispatcher;
    @NonNull
    private final MessageSender messageSender;

    @Override
    public void onUpdateReceived(Update update) {
        commandDispatcher.dispatch(update);
    }

    @PostConstruct
    public void init() {
        messageSender.setBot(this);
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
