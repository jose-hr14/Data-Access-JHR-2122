package com.example.springexample.model.dao;

import com.example.springexample.model.entities.DeptEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeptEntityDAO extends CrudRepository<DeptEntity, Integer> {
}
