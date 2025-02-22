package com.pavyk96.TgBot.handler;

import com.pavyk96.TgBot.utils.MessageSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler{

    @NonNull
    private final MessageSender messageSender;

    @Override
    public boolean canHandle(String command) {
        return command.equals("/start");
    }

    @Override
    public void handle(Update update) {
        long chatId = update.getMessage().getChatId();
        String text = "Привет! 👋 Добро пожаловать в бота.\n" +
                "Для просмотра курсов напиши /courses";
        messageSender.sendMessage(chatId, text);
    }
}
