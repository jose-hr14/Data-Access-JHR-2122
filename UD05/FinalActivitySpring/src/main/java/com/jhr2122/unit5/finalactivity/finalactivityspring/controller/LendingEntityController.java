package com.jhr2122.unit5.finalactivity.finalactivityspring.controller;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.ILendingEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IUsersEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.LendingEntity;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/Library/Lending")
public class LendingEntityController {

    @Autowired
    private ILendingEntityDAO lendingEntityDAO;

    @GetMapping
    public List<LendingEntity> findAllUsers(){
        return (List<LendingEntity>) lendingEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LendingEntity> findUserById(@PathVariable(value = "id") Integer id) {
        Optional<LendingEntity> lending = lendingEntityDAO.findById(id);

        if(lending.isPresent()) {
            return  ResponseEntity.ok().body(lending.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public LendingEntity saveUser(@Validated @RequestBody LendingEntity lending) {
        return lendingEntityDAO.save(lending);
    }
}
