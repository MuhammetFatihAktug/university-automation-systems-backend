package org.dpu.collageautomationsystemsbackend.entities.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpu.collageautomationsystemsbackend.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "students")
public class Student implements UserDetails {

    @Id
    @Column(name = "student_id")
    private Long studentNumber;

    @Column(name = "tc")
    private Long tc;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    private String email;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
}