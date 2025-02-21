package com.pavyk96.TgBot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "LessonProgress")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

