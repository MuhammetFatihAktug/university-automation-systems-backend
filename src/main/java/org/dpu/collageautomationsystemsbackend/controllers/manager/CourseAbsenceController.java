package org.dpu.collageautomationsystemsbackend.controllers.manager;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsenceRequest;
import org.dpu.collageautomationsystemsbackend.services.CourseAbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/absences")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080", "http://192.168.56.1:4200"})
public class CourseAbsenceController {

    private final CourseAbsenceService courseAbsenceService;

    @PostMapping("/save")
    public ResponseEntity<CourseAbsence> createAbsence(@RequestBody CourseAbsenceRequest  request) {
        CourseAbsence absence = new CourseAbsence();
        absence.setDate(request.getDate());
        absence.setAbsenceStatus(request.isAbsenceStatus());

        CourseAbsence savedAbsence = courseAbsenceService.saveAbsence(absence, request.getStudentNumber(), request.getCourseCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAbsence);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<CourseAbsence>> createAbsences(@RequestBody List<CourseAbsenceRequest> requests) {
        List<CourseAbsence> savedAbsences = new ArrayList<>();
        for (CourseAbsenceRequest request : requests) {
            CourseAbsence absence = new CourseAbsence();
            absence.setDate(request.getDate());
            absence.setAbsenceStatus(request.isAbsenceStatus());

            CourseAbsence savedAbsence = courseAbsenceService.saveAbsence(absence, request.getStudentNumber(), request.getCourseCode());
            savedAbsences.add(savedAbsence);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAbsences);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAbsence(@RequestBody CourseAbsenceRequest request) {
        courseAbsenceService.deleteAbsenceByDateAndStudentCourse(request.getStudentNumber(), request.getCourseCode(), request.getDate());
        return ResponseEntity.noContent().build();
    }
}
