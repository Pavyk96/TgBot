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

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserCourseHandler implements CommandHandler {

    private final UserService userService;
    private final CourseService courseService;
    private final UserCourseService userCourseService;
    private final MessageSender messageSender;

    @Override
    public boolean canHandle(String command) {
        return command.startsWith("✅ Записаться на курс ");
    }

    @Override
    public void handle(Update update) {
        if (update.getMessage() == null || update.getMessage().getText() == null) {
            return;
        }

        long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText();

        String courseTitle = command.replaceFirst("✅ Записаться на курс ", "");

        User user = userService.getUserByChatId(chatId);
        Course course = courseService.getCourseByTitle(courseTitle);
        userCourseService.createUserCourse(UserCourse.builder()
                .userId(user)
                .course(course)
                .startDate(LocalDate.now())
                .build());


        if (course == null) {
            messageSender.sendMessage(chatId, "Курс не найден.");
            return;
        }

        messageSender.sendMessage(chatId, "Вы записались на курс: " + courseTitle);
    }


}
