package com.pavyk96.TgBot.service;

import com.pavyk96.TgBot.models.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);

    Course saveCourse(Course course);

    void deleteCourse(Long id);
}

