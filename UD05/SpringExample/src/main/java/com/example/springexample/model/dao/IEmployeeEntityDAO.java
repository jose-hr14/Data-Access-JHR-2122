package com.example.springexample.model.dao;

import com.example.springexample.model.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeEntityDAO extends CrudRepository<EmployeeEntity, Integer> {

}
