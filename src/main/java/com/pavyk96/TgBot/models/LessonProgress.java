package com.pavyk96.TgBot.models;

import jakarta.persistence.*;

import java.time.LocalDate;

public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    private LocalDate unlockDate;
}

