package com.pavyk96.TgBot.service.impl;

import com.pavyk96.TgBot.models.UserCourse;
import com.pavyk96.TgBot.repository.UserCourseRepository;
import com.pavyk96.TgBot.service.UserCourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {
    @NonNull
    private final UserCourseRepository userCourseRepository;

    @Override
    public UserCourse createUserCourse(UserCourse userCourse) {
        Optional<UserCourse> existing = userCourseRepository.findByUser_ChatIdAndCourse_Id(
                userCourse.getUser().getChatId(),
                userCourse.getCourse().getId()
        );

        return existing.orElseGet(() -> userCourseRepository.save(userCourse));
    }


    @Override
    public UserCourse updateUserCourse(Long id, UserCourse userCourse) {
        return userCourseRepository.findById(id)
                .map(existing -> {
                    existing.setCourse(userCourse.getCourse());
                    existing.setUser(userCourse.getUser());
                    existing.setStartDate(userCourse.getStartDate());
                    return userCourseRepository.save(existing);
                })
                .orElse(null);
    }

    @Override
    public void deleteCourseById(Long idUser, Long idCourse) {
        Optional<UserCourse> userCourse = userCourseRepository.findByUser_ChatIdAndCourse_Id(idUser, idCourse);
        userCourse.ifPresent(userCourseRepository::delete);
    }

    @Override
    public UserCourse getUserCourse(Long userId, Long courseId) {
        return userCourseRepository.findByUser_ChatIdAndCourse_Id(userId, courseId).orElse(null);
    }
}
