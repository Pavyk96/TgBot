package com.pavyk96.TgBot.repository;

import com.pavyk96.TgBot.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByActiveTrue();
    Course findByTitle(String title);
}
