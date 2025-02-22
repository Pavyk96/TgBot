package com.pavyk96.TgBot.service;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.models.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<CourseDTO> getAllCourses();

    List<CourseDTO> getActiveCourses();

    CourseDTO getCourseById(Long id);

    CourseDTO saveCourse(Course course);

    void deleteCourse(Long id);
}

