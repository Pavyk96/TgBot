package com.pavyk96.TgBot.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
}

