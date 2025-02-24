package com.pavyk96.TgBot.repository;

import com.pavyk96.TgBot.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    Optional<UserCourse> findByUser_ChatIdAndCourse_Id(Long chatId, Long courseId);
}
