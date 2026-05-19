package com.example.dsitask1.controller;

import com.example.dsitask1.model.Student;
import com.example.dsitask1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Student> students = studentRepository.findAll();

        model.addAttribute("totalStudents", students.size());

        students.stream()
            .map(Student::getEnrollmentDate)
            .max(LocalDate::compareTo)
            .ifPresentOrElse(
                date -> model.addAttribute("lastUpdated",
                    date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))),
                () -> model.addAttribute("lastUpdated", "No records yet")
            );

        return "index";
    }
}