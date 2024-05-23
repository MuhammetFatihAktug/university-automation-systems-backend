package org.dpu.collageautomationsystemsbackend.dto;

import java.util.Date;

public record StudentDTO(
        Long studentNumber,
        long tc,
        String firstName,
        String lastName,
        String phoneNumber,
        Date birthDate,
        char gender,
        String address,
        int grade,
        Date registrationDate,
        String curriculum,
        String studyDurationStatus,
        String tuitionStatus,
        String password

) {
}