package com.pavyk96.TgBot.service;

import com.pavyk96.TgBot.config.BotConfig;
import com.pavyk96.TgBot.models.Course;
import com.pavyk96.TgBot.repository.CourseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TgBot extends TelegramLongPollingBot {

    @NonNull
    private final BotConfig botConfig;
    @NonNull
    private final CourseRepository courseRepository;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Chat chat = update.getMessage().getChat();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendCourseMenu(chatId);
                    break;
                default:
                    sendMessage(chatId, "Прости, но я не знаю такой команды(");
            }
        }
    }

    private void sendCourseMenu(long chatId) {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            sendMessage(chatId, "Пока нет доступных курсов.");
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите курс:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Course course : courses) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(course.getTitle());
            button.setCallbackData("course_" + course.getId());
            rows.add(List.of(button));
        }

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
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
