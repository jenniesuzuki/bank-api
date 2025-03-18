package br.com.fiap.bank_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    private static final String projectName = "Bank API";
    private static final String author = "Jennifer Kaori Suzuki RM554661";

    @GetMapping
    public String projectDetails (){
        return projectName + " - " + "Author: " + author;
    }
}