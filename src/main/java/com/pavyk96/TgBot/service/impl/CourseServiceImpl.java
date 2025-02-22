package com.pavyk96.TgBot.service.impl;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.mapper.CourseMapper;
import com.pavyk96.TgBot.models.Course;
import com.pavyk96.TgBot.repository.CourseRepository;
import com.pavyk96.TgBot.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    @NonNull
    private final CourseRepository repository;

    @Override
    public List<CourseDTO> getAllCourses() {
        return repository.findAll()
                .stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getActiveCourses() {
        return repository.findByActiveTrue()
                .stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        return repository.findById(id)
                .map(CourseMapper::toDto)
                .orElse(null);
    }


    @Override
    public CourseDTO saveCourse(Course course) {
        return CourseMapper.toDto(repository.save(course));
    }

    @Override
    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Course getCourseByTitle(String title) {
        return repository.findByTitle(title);
    }
}
