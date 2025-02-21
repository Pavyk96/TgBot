package com.pavyk96.TgBot.mapper;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.models.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDto(Course course) {
        return CourseDTO.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .imageUrl(course.getImageUrl())
                .build();
    }

    public Course toModel(CourseDTO dto) {
        return Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
    }
}
