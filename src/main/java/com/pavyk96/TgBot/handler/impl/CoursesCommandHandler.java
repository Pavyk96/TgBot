package com.pavyk96.TgBot.handler.impl;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.handler.CommandHandler;
import com.pavyk96.TgBot.service.CourseService;
import com.pavyk96.TgBot.utils.MessageSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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
        return command.equals("/start") || command.equals("⬅ Назад к курсам");
    }

    @Override
    public void handle(Update update) {
        long chatId = update.getMessage().getChatId();
        List<CourseDTO> coursesList = courseService.getActiveCourses();

        if (coursesList.isEmpty()) {
            messageSender.sendMessage(chatId, "Пока нет доступных курсов.");
            return;
        }

        List<KeyboardRow> keyboardRows = coursesList.stream()
                .map(course -> {
                    KeyboardButton button = new KeyboardButton(course.getTitle());
                    KeyboardRow row = new KeyboardRow();
                    row.add(button);
                    return row;
                })
                .collect(Collectors.toList());

        messageSender.sendMessageWithKeyboard(chatId, "📚 *Доступные курсы:*", keyboardRows);
    }
}
