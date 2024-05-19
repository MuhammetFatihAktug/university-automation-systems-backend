package org.dpu.collageautomationsystemsbackend.entities.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id")
    private Long studentNumber;

    @Column(name = "tc")
    private long tc;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 20)
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(length = 1)
    private char gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    private int grade;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column(length = 50)
    private String curriculum;

    @Column(length = 30)
    private String studyDurationStatus;

    @Column(length = 10)
    private String tuitionStatus;

    @OneToMany(mappedBy = "student")
    private Set<StudentCourse> studentCourses;

    private String password;
}