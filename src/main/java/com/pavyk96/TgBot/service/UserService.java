package com.pavyk96.TgBot.service;

import com.pavyk96.TgBot.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUserById(Long id, User user);
    void deleteUserById(Long id);
    User getUserByChatId(Long chatId);
}
