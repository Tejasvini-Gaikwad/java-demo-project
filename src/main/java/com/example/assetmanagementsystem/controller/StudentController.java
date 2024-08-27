package com.example.assetmanagementsystem.controller;

import com.example.assetmanagementsystem.dtos.StudentPostRequest;
import com.example.assetmanagementsystem.dtos.StudentResponse;
import com.example.assetmanagementsystem.entities.Student;
import com.example.assetmanagementsystem.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/")
    public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentPostRequest student) {
        return studentService.addStudent(student);
    }
}
