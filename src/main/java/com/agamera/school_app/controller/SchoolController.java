package com.agamera.school_app.controller;

import com.agamera.school_app.entity.School;
import com.agamera.school_app.entity.Student;
import com.agamera.school_app.repository.SchoolRepository;
import com.agamera.school_app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    // 1. Get all schools
    @GetMapping("/schools")
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    // 2. Add a new School
    @PostMapping("/schools")
    public School createSchool(@RequestBody School school) {
        return schoolRepository.save(school);
    }

    // 3. Add a Student to a specific School
    @PostMapping("/schools/{schoolId}/students")
    public ResponseEntity<Student> addStudent(@PathVariable Long schoolId, @RequestBody Student student) {
        return schoolRepository.findById(schoolId).map(school -> {
            student.setSchool(school);
            return ResponseEntity.ok(studentRepository.save(student));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 4. DELETE SCHOOL (This will automatically delete associated students due to CascadeType.ALL)
    @DeleteMapping("/schools/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
        if (!schoolRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        schoolRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // 5. DELETE STUDENT
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}