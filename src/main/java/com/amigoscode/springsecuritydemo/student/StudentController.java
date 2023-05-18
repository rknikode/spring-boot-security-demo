package com.amigoscode.springsecuritydemo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private static final List<Student> STUDENT_LIST = Arrays.asList(
            new Student(1, "John Cena"),
            new Student(2, "Alexa Bliss"),
            new Student(3, "Brie Bella")
    );

    @GetMapping(path = "/{studentId}")
    public Student getStudentById(@PathVariable Integer studentId) {
        return STUDENT_LIST.stream()
                           .filter(student -> student.getStudentId().equals(studentId))
                           .findFirst()
                           .orElseThrow(() -> new IllegalStateException("Student" + studentId + "does not exists"));
    }
}
