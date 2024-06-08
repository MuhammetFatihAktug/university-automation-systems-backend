package org.dpu.collageautomationsystemsbackend.entities.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @Column(name = "course_code")
    private int courseCode;

    @Column(name = "semester", nullable = false)
    private int semester;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "credits", nullable = false)
    private int credits;

    @Column(name = "ects", nullable = false)
    private int ects;


}