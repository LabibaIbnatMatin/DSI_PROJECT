package com.example.dsitask1.controller;

import com.example.dsitask1.dto.StudentDTO;
import com.example.dsitask1.model.Student;
import com.example.dsitask1.service.DuplicateEmailException;
import com.example.dsitask1.service.StudentNotFoundException;
import com.example.dsitask1.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Display list of all students
     */
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.listStudents();
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "students/list";
    }

    /**
     * Show student creation form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("isNew", true);
        return "students/form";
    }

    /**
     * Create new student
     */
    @PostMapping
    public String createStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "students/form";
        }

        try {
            studentService.createStudent(studentDTO);
            redirectAttributes.addFlashAttribute("message", "Student created successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (DuplicateEmailException e) {
            bindingResult.rejectValue("email", "error.email", e.getMessage());
            model.addAttribute("isNew", true);
            return "students/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error creating student: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/students";
    }

    /**
     * Show student details
     */
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getStudent(id);
            model.addAttribute("student", student);
        } catch (StudentNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/students";
        }
        return "students/details";
    }

    /**
     * Show student edit form
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        StudentDTO studentDTO;
        try {
            studentDTO = studentService.toDto(studentService.getStudent(id));
        } catch (StudentNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/students";
        }
        model.addAttribute("studentDTO", studentDTO);
        model.addAttribute("isNew", false);
        return "students/form";
    }

    /**
     * Update student
     */
    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
            @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", false);
            return "students/form";
        }

        try {
            studentService.updateStudent(id, studentDTO);
            redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (StudentNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/students";
        } catch (DuplicateEmailException e) {
            bindingResult.rejectValue("email", "error.email", e.getMessage());
            model.addAttribute("isNew", false);
            return "students/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error updating student: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/students";
    }

    /**
     * Delete student
     */
    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (StudentNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting student: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/students";
    }
}