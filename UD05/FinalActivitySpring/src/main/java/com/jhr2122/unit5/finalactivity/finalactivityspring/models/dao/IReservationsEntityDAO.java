package com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.ReservationsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationsEntityDAO extends CrudRepository<ReservationsEntity, Integer> {
}
