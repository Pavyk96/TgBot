package com.pavyk96.TgBot.handler;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.service.CourseService;
import com.pavyk96.TgBot.utils.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseInfoHandler implements CallbackHandler {

    private final CourseService courseService;
    private final MessageSender messageSender;

    @Override
    public boolean canHandle(String callbackData) {
        return callbackData.startsWith("course_");
    }

    @Override
    public void handle(Update update) {
        var callbackQuery = update.getCallbackQuery();
        if (callbackQuery == null) return;

        long chatId = callbackQuery.getMessage().getChatId();
        String courseIdStr = callbackQuery.getData().substring(7); // "course_"

        long courseId = parseCourseId(courseIdStr, chatId);
        if (courseId == -1) return;

        CourseDTO course = courseService.getCourseById(courseId);
        if (course == null) {
            messageSender.sendMessage(chatId, "Курс не найден.");
            return;
        }

        String courseInfo = "*Курс:* " + course.getTitle() + "\n\n" + course.getDescription();
        List<List<InlineKeyboardButton>> keyboard = List.of(
                List.of(
                        InlineKeyboardButton.builder().text("⬅ Назад").callbackData("/courses").build(),
                        InlineKeyboardButton.builder().text("✅ Записаться").callbackData("/enroll_" + courseId).build()
                )
        );

        messageSender.sendMenu(chatId, courseInfo, keyboard);
    }

    private long parseCourseId(String courseIdStr, long chatId) {
        try {
            return Long.parseLong(courseIdStr);
        } catch (NumberFormatException e) {
            messageSender.sendMessage(chatId, "Некорректный идентификатор курса.");
            return -1;
        }
    }
}
