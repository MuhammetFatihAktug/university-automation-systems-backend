package org.dpu.collageautomationsystemsbackend.entities.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false)

    private String studentNumber;
    @Column(nullable = false)

    private String name;
    @Column(nullable = false)

    private String lastName;
    @Column(nullable = false)

    private String tc;
    @Column(nullable = false)

    private String phoneNumber;
    @Column(nullable = false)

    private Date birthDay;
    @Column(nullable = false)

    private String gender;
    @Column(nullable = false)

    private String address;
    @Column(nullable = false)

    private Date registerDate;

    @Column(nullable = false)
    private String password;
}
