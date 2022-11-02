package com.workshop.tddworkshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BecaEstudianteController {

    @GetMapping(value ="v1/becas/alumno/")
    public String getStudentInfo(){
        return "Fernanda";
    }
}
