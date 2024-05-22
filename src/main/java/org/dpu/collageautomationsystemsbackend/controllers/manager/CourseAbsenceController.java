package org.dpu.collageautomationsystemsbackend.controllers.manager;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.services.CourseAbsenceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/absences")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class CourseAbsenceController {

    private final CourseAbsenceService courseAbsenceService;

    @GetMapping("/{studentNumber}/all")
    public List<CourseAbsence> getAllAbsences(@PathVariable("studentNumber") Long studentNumber) {
        return courseAbsenceService.getAllAbsences(studentNumber);
    }

    @GetMapping("/{studentNumber}/course")
    public ResponseEntity<List<CourseAbsence>> getAbsenceByCourse(@RequestParam("courseCode") int courseCode, @PathVariable("studentNumber") Long studentNumber) {
        List<CourseAbsence> absence = courseAbsenceService.getAbsenceByCourse(studentNumber, courseCode);
        return ResponseEntity.ok(absence);
    }

    @PostMapping("/{studentNumber}/save")
    public CourseAbsence createAbsence(@PathVariable("studentNumber") Long studentNumber, @RequestParam("courseCode") int courseCode, @RequestBody CourseAbsence absence) {
        return courseAbsenceService.saveAbsence(absence, studentNumber, courseCode);
    }


    @DeleteMapping("/{studentNumber}/delete/course")
    public ResponseEntity<Void> deleteAbsence(@PathVariable("studentNumber") Long studentNumber, @RequestParam("courseCode") int courseCode, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        courseAbsenceService.deleteAbsenceByDateAndStudentCourse(studentNumber, courseCode, date);
        return ResponseEntity.noContent().build();
    }
}