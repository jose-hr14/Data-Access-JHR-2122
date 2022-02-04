package com.example.springexample.controllers;

import com.example.springexample.model.dao.IEmployeeEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-rest-jhr2122/Employees")
public class EmployeeController {
    @Autowired
    private IEmployeeEntityDAO employeeDAO;
}
