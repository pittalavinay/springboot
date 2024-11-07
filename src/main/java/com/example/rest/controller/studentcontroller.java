package com.example.rest.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.Repo.StudentRepo;
import com.example.rest.entity.Student;

import ch.qos.logback.classic.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class studentcontroller {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(studentcontroller.class);

    @Autowired
    StudentRepo studentRepo;

    @PostMapping("api/students")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        try {
            logger.info("Saving student: {}", student);
            studentRepo.save(student);
            return new ResponseEntity<>("Student saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving student", e);
            return new ResponseEntity<>("Error saving student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("api/students/{name}")
    @Transactional
    public ResponseEntity<String> deleteStudent(@PathVariable String name) {
        try {
            if (studentRepo.existsByValue(name)) {
                studentRepo.deleteByValue(name);
                logger.info("Deleted student with name: {}", name);
                return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting student with name: {}", name, e);
            return new ResponseEntity<>("Error deleting student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
