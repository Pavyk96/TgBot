package com.pavyk96.TgBot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "lessons")
@Data
@Builder
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Название урока
    private String content; // Описание урока
    private String fileUrl; // Ссылка на PDF или видео

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
}
