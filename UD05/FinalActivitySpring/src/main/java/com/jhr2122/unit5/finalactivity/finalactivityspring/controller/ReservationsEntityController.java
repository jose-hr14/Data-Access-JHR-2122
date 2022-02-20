package com.jhr2122.unit5.finalactivity.finalactivityspring.controller;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao.IReservationsEntityDAO;
import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.ReservationsEntity;
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
    public List<ReservationsEntity> findAllReservations(){
        return (List<ReservationsEntity>) reservationsEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationsEntity> findReservationById(@PathVariable(value = "id") Integer id) {
        Optional<ReservationsEntity> reservation = reservationsEntityDAO.findById(id);

        if(reservation.isPresent()) {
            return  ResponseEntity.ok().body(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ReservationsEntity saveReservation(@Validated @RequestBody ReservationsEntity reservationsEntity) {
        return reservationsEntityDAO.save(reservationsEntity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable(value = "id") int id) {
        Optional<ReservationsEntity> reservation = reservationsEntityDAO.findById(id);
        if(reservation.isPresent()) {
            reservationsEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
