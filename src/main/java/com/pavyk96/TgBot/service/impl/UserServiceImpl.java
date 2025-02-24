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
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseGet(() -> createNewUser(id));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(Long id, User user) {
        if (!userRepository.existsById(id)) {
            return null;
        }
        user.setChatId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByChatId(Long chatId) {
        return userRepository.findById(chatId)
                .orElseGet(() -> createNewUser(chatId));
    }

    private User createNewUser(Long chatId) {
        User user = User.builder()
                .chatId(chatId)
                .build();

        return userRepository.save(user);
    }
}
