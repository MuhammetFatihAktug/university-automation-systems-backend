package org.dpu.collageautomationsystemsbackend.entities.student;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "student_courses")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_code", nullable = false)
    private Course course;

    @Column(name = "midterm", nullable = false)
    private Long midterm;

    @Column(name = "final", nullable = false)
    private Long finalExam;

    @Column(name = "makeup", nullable = false)
    private Long makeup;

    @Column(name = "average", nullable = false)
    private Long average;

    @Column(name = "letter_grade", nullable = false)
    private String letterGrade;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Temporal(TemporalType.DATE)
    private String createdDate;

}