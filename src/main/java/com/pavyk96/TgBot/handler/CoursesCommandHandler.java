package com.pavyk96.TgBot.handler;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.service.CourseService;
import com.pavyk96.TgBot.utils.MessageSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoursesCommandHandler implements CommandHandler {

    @NonNull
    private final MessageSender messageSender;
    @NonNull
    private final CourseService courseService;

    @Override
    public boolean canHandle(String command) {
        return command.equals("/courses");
    }

    @Override
    public void handle(Update update) {
        long chatId = update.getMessage().getChatId();
        List<CourseDTO> coursesList = courseService.getActiveCourses();

        if (coursesList.isEmpty()) {
            messageSender.sendMessage(chatId, "Пока нет доступных курсов.");
            return;
        }

        List<List<InlineKeyboardButton>> keyboard = coursesList.stream()
                .map(course -> List.of(
                        InlineKeyboardButton.builder()
                                .text(course.getTitle())
                                .callbackData("course_" + course.getId())
                                .build()
                ))
                .collect(Collectors.toList());

        messageSender.sendMenu(chatId, "📚 *Доступные курсы:*", keyboard);
    }
}
