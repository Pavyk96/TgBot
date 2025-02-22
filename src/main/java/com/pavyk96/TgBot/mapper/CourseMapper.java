package com.pavyk96.TgBot.mapper;

import com.pavyk96.TgBot.dto.CourseDTO;
import com.pavyk96.TgBot.models.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public static CourseDTO toDto(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .imageUrl(course.getImageUrl())
                .build();
    }

    public static Course toModel(CourseDTO dto) {
        return Course.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
    }
}
