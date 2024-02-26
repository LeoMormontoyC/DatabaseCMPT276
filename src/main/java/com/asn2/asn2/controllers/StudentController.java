package com.asn2.asn2.controllers;

//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.asn2.asn2.models.Student;
import com.asn2.asn2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    // displaying the students
    @GetMapping("/students/view")
    public String getAllStudents(Model model) {
        System.out.println("Getting all students");
        // get all students from database
        List<Student> students = studentRepo.findAll();
        // end of reading from database
        model.addAttribute("stu", students);
        return "students/showAll";
    }

    @PostMapping("students/add")
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response) {
        System.out.println("Adding student");
        String newName = newstudent.get("name");
        int newWeight = Integer.parseInt(newstudent.get("weight"));
        int newHeight = Integer.parseInt(newstudent.get("height"));
        int newAge = Integer.parseInt(newstudent.get("age"));
        String newHairColor = newstudent.get("hairColor");
        double newGpa = Double.parseDouble(newstudent.get("gpa"));

        studentRepo.save(new Student(newName, newWeight, newHeight, newAge, newHairColor, newGpa));
        response.setStatus(201);
        return "redirect:/students/view";
    }

    // POST request to delete student
    @PostMapping("/students/remove/{studentId}")
    public String removeStudent(@PathVariable(value = "studentId") int studentId, HttpServletResponse response) {
        System.out.println("REMOVE user studentId:");
        System.out.println(studentId);
        Optional<Student> userRecord = studentRepo.findById(studentId);

        if (userRecord.isPresent()) {
            System.out.println("[REMOVE]Success");
            studentRepo.deleteById(studentId);
            response.setStatus(201);
        } else {
            System.out.println("[REMOVE]Record not found");
        }
        return "redirect:/students/view";
    }

    @GetMapping("/students/edit/{studentId}")
    public String gotoEditStudent(@PathVariable(value = "studentId") int studentId, Model model,
            HttpServletResponse response) {
        try {
            Student student = studentRepo.findById(studentId).orElseThrow(() -> (new Exception("null")));
            model.addAttribute("stu", student);
            model.addAttribute("stuId", studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "students/edit";
    }

    @PostMapping("/students/edit/{studentId}")
    public String editStudent(
            @PathVariable int studentId,
            @RequestParam Map<String, String> newStudent,
            HttpServletResponse response,
            Model model) {
        try {
            System.out.println("EDIT student id:");
            System.out.println(studentId);
            String newName = newStudent.get("newName");
            int newWeight = Integer.parseInt(newStudent.get("newWeight"));
            int newHeight = Integer.parseInt(newStudent.get("newHeight"));
            String newHairColor = newStudent.get("newHairColor");
            double newGpa = Double.parseDouble(newStudent.get("newGpa"));
            Student student = studentRepo.findById(studentId).orElseThrow(() -> new Exception("Student not found"));
            student.setName(newName);
            student.setWeight(newWeight);
            student.setHeight(newHeight);
            student.setHairColor(newHairColor);
            student.setGpa(newGpa);
            studentRepo.save(student);

            // Fetch the updated list of students
            List<Student> students = studentRepo.findAll();
            model.addAttribute("students", students);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Redirect to the view page after editing
        return "redirect:/students/view";
    }

    @GetMapping("/students/visualize")
    public String visualizeStudents(Model model) {
        List<Student> students = studentRepo.findAll();
        model.addAttribute("stu", students);
        return "/students/visualize";
    }
}
