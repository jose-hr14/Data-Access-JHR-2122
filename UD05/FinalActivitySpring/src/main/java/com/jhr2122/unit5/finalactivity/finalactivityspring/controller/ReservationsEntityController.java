package com.jhr2122.unit5.finalactivity.finalactivityspring.controller;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IReservationsEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IUsersEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.ReservationsEntity;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/Library/Reservations")
public class ReservationsEntityController {

    @Autowired
    private IReservationsEntityDAO reservationsEntityDAO;

    @GetMapping
    public List<ReservationsEntity> findAllUsers(){
        return (List<ReservationsEntity>) reservationsEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationsEntity> findUserById(@PathVariable(value = "id") Integer id) {
        Optional<ReservationsEntity> user = reservationsEntityDAO.findById(id);

        if(user.isPresent()) {
            return  ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ReservationsEntity saveUser(@Validated @RequestBody ReservationsEntity reservationsEntity) {
        return reservationsEntityDAO.save(reservationsEntity);
    }
}
