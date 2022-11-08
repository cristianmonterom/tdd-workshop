package com.workshop.tddworkshop.controllers;

import com.workshop.tddworkshop.controllers.dto.StudentDTO;
import com.workshop.tddworkshop.model.Student;
import com.workshop.tddworkshop.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BecaEstudianteController {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping(value ="v1/becas/alumno/{id}")
    public StudentDTO getStudentInfo(@PathVariable("id") long id){

        Student student = studentRepository.findById(id);
        StudentDTO studentDTO = new StudentDTO(student.getId(), student.getName());
        return studentDTO;
    }
}
