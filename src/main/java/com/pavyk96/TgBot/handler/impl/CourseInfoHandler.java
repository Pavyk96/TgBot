package com.pavyk96.TgBot.handler.impl;

import com.pavyk96.TgBot.handler.CommandHandler;
import com.pavyk96.TgBot.models.Course;
import com.pavyk96.TgBot.models.User;
import com.pavyk96.TgBot.models.UserCourse;
import com.pavyk96.TgBot.service.CourseService;
import com.pavyk96.TgBot.service.UserCourseService;
import com.pavyk96.TgBot.service.UserService;
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
    private final UserService userService;
    private final UserCourseService userCourseService;
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

        User user = userService.getUserByChatId(chatId);
        boolean isEnrolled = userCourseService.getUserCourse(user.getChatId(), course.getId()) != null;

        String courseInfo = "*Курс:* " + course.getTitle() + "\n\n" + course.getDescription();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("⬅ Назад к курсам"));

        if (isEnrolled) {
            row.add(new KeyboardButton("📖 Перейти к курсу " + courseTitle));
        } else {
            row.add(new KeyboardButton("✅ Записаться на курс " + courseTitle));
        }

        List<KeyboardRow> keyboard = List.of(row);

        messageSender.sendMessageWithKeyboard(chatId, courseInfo, keyboard);
    }
}
