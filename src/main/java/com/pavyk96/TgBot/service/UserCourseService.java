package com.pavyk96.TgBot.service;

import com.pavyk96.TgBot.models.UserCourse;

public interface UserCourseService {
    UserCourse createUserCourse(UserCourse userCourse);
    UserCourse updateUserCourse(Long id, UserCourse userCourse);
    void deleteCourseById(Long idUser, Long idCourse);
    UserCourse getUserCourse(Long userId, Long courseId);

}
