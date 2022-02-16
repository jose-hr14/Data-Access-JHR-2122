package com.jhr2122.unit5.finalactivity.finalactivityspring.controller;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IBooksEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IUsersEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.BooksEntity;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/Library/Book")
public class BooksEntityController {

    @Autowired
    private IBooksEntityDAO booksEntityDAO;

    @GetMapping
    public List<BooksEntity> findAllUsers(){
        return (List<BooksEntity>) booksEntityDAO.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<BooksEntity> findUserById(@PathVariable(value = "isbn") String isbn) {
        Optional<BooksEntity> book = booksEntityDAO.findById(isbn);

        if(book.isPresent()) {
            return  ResponseEntity.ok().body(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public BooksEntity saveUser(@Validated @RequestBody BooksEntity book) {
        return booksEntityDAO.save(book);
    }
}
