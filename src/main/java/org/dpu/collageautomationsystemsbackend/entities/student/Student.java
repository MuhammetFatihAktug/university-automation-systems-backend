package org.dpu.collageautomationsystemsbackend.entities.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentNumber;

    private String name;

    private String lastName;

    private String tc;

    private String phoneNumber;


    private Date birthDay;

    private String gender;

    private String address;


    private Date registerDate;

    @Column(nullable = false)
    private String password;
}
