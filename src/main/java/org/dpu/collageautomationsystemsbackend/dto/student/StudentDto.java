package org.dpu.collageautomationsystemsbackend.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {

    private Long id;
    private String studentNumber;
    private String name;
    private String lastName;
    private String token;

}
