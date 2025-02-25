package com.pavyk96.TgBot.handler.impl;

import com.pavyk96.TgBot.handler.CommandHandler;
import com.pavyk96.TgBot.models.Course;
import com.pavyk96.TgBot.models.Module;
import com.pavyk96.TgBot.service.CourseService;
import com.pavyk96.TgBot.utils.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleHandler implements CommandHandler {

    private final CourseService courseService;
    private final MessageSender messageSender;

    @Override
    public boolean canHandle(String command) {
        return command.startsWith("📖 Перейти к курсу ");
    }

    @Override
    public void handle(Update update) {
        if (update.getMessage() == null || update.getMessage().getText() == null) {
            return;
        }

        long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText();

        String courseTitle = command.replace("📖 Перейти к курсу ", "").trim();
        Course course = courseService.getCourseByTitle(courseTitle);

        if (course == null) {
            messageSender.sendMessage(chatId, "Курс не найден.");
            return;
        }

        List<Module> modules = course.getModules();
        if (modules == null || modules.isEmpty()) {
            messageSender.sendMessage(chatId, "У этого курса нет модулей.");
            return;
        }

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(module.getTitle());
            button.setCallbackData("module_" + module.getId());

            row.add(button);

            // Если в строке 4 кнопки или это последняя кнопка - добавляем строку в список
            if (row.size() == 4 || i == modules.size() - 1) {
                inlineButtons.add(new ArrayList<>(row));
                row.clear();
            }
        }

        messageSender.sendMessageWithInlineKeyboard(chatId, "Модули курса:", inlineButtons);
    }
}
