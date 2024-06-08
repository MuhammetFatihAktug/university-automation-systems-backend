package org.dpu.collageautomationsystemsbackend.entities.student;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseAbsenceRequest {
    private Long studentNumber;
    private int courseCode;
    private Date date;
    private boolean absenceStatus;


}