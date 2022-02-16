package com.jhr2122.unit5.finalactivity.finalactivityspring.controller;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IUsersEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/Library/Users")
public class UsersEntityController {

    @Autowired
    private IUsersEntityDAO usersEntityDAO;

    @GetMapping
    public List<UsersEntity> findAllUsers(){
        return (List<UsersEntity>) usersEntityDAO.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<UsersEntity> findUserById(@PathVariable(value = "code") String code) {
        Optional<UsersEntity> user = usersEntityDAO.findById(code);

        if(user.isPresent()) {
            return  ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public UsersEntity saveUser(@Validated @RequestBody UsersEntity user) {
        return usersEntityDAO.save(user);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "code") String code) {
        Optional<UsersEntity> user = usersEntityDAO.findById(code);
        if(user.isPresent()) {
            usersEntityDAO.deleteById(code);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
