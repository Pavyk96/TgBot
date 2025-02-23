package com.pavyk96.TgBot.service.impl;

import com.pavyk96.TgBot.models.User;
import com.pavyk96.TgBot.repository.UserRepository;
import com.pavyk96.TgBot.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @NonNull
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Возвращаем всех пользователей из базы
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseGet(() -> createNewUser(id)); // Если не найдено, создаем нового пользователя
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user); // Сохраняем нового пользователя
    }

    @Override
    public User updateUserById(Long id, User user) {
        if (!userRepository.existsById(id)) {
            return null; // Если пользователя нет, возвращаем null
        }
        user.setChatId(id);
        return userRepository.save(user); // Обновляем пользователя
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id); // Удаляем пользователя по ID
    }

    @Override
    public User getUserByChatId(Long chatId) {
        return userRepository.findById(chatId)
                .orElseGet(() -> createNewUser(chatId)); // Если не найдено, создаем нового
    }

    private User createNewUser(Long chatId) {
        // Создаем нового пользователя с chatId и пустыми полями
        User user = User.builder()
                .chatId(chatId)
                .firstName(null)
                .lastName(null)
                .username(null)
                .isAlert(null) // Поля могут быть null
                .build();

        return userRepository.save(user); // Сохраняем нового пользователя в базе
    }
}
