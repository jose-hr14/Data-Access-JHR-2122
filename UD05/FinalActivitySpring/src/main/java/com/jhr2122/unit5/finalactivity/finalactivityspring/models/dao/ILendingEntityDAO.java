package com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.LendingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILendingEntityDAO extends CrudRepository<LendingEntity, Integer> {
}
