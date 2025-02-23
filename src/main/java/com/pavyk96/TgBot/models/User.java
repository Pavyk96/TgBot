package com.pavyk96.TgBot.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long chatId;

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isAlert;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCourse> enrolledCourses = new HashSet<>();
}
