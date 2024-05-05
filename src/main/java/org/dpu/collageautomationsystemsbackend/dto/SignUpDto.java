package org.dpu.collageautomationsystemsbackend.dto;

import java.util.Date;

public record SignUpDto(String studentNumber, String name, String lastName, String tc, String phoneNumber,
                        Date birthDay, String gender, String address, Date registerDate, String password) {
}
