package com.pavyk96.TgBot.handler.impl;

import com.pavyk96.TgBot.handler.CommandHandler;
import com.pavyk96.TgBot.models.Course;
import com.pavyk96.TgBot.service.CourseService;
import com.pavyk96.TgBot.utils.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseInfoHandler implements CommandHandler {

    private final CourseService courseService;
    private final MessageSender messageSender;

    @Override
    public boolean canHandle(String command) {
        return courseService.getCourseByTitle(command) != null;
    }

    @Override
    public void handle(Update update) {
        if (update.getMessage() == null || update.getMessage().getText() == null) {
            return;
        }

        long chatId = update.getMessage().getChatId();
        String courseTitle = update.getMessage().getText();

        Course course = courseService.getCourseByTitle(courseTitle);
        if (course == null) {
            messageSender.sendMessage(chatId, "Курс не найден.");
            return;
        }

        String courseInfo = "*Курс:* " + course.getTitle() + "\n\n" + course.getDescription();

        // Создание клавиатуры с кнопками "Назад" и "Записаться на курс"
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("⬅ Назад к курсам"));
        row.add(new KeyboardButton("✅ Записаться на курс"));

        List<KeyboardRow> keyboard = List.of(row);

        // Отправка сообщения с клавиатурой
        messageSender.sendMessageWithKeyboard(chatId, courseInfo, keyboard);
    }
}
