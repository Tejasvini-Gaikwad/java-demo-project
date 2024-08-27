package com.example.assetmanagementsystem.services;

import com.example.assetmanagementsystem.dtos.StudentPostRequest;
import com.example.assetmanagementsystem.dtos.StudentResponse;
import com.example.assetmanagementsystem.entities.Student;
import com.example.assetmanagementsystem.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseEntity<StudentResponse> addStudent(StudentPostRequest student) {
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setSurname(student.getSurname());
        newStudent.setCreated(new Date());
        newStudent.setUpdated(new Date());

        Student savedStudent = studentRepository.save(newStudent);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(savedStudent.getId());
        studentResponse.setName(savedStudent.getName());
        studentResponse.setSurname(savedStudent.getSurname());
        studentResponse.setCreated(savedStudent.getCreated());
        studentResponse.setUpdated(savedStudent.getUpdated());

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }
}
