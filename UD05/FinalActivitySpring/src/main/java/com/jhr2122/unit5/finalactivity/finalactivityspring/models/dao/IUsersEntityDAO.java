package com.jhr2122.unit5.finalactivity.finalactivityspring.models.dao;

import com.jhr2122.unit5.finalactivity.finalactivityspring.models.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersEntityDAO extends CrudRepository<UsersEntity, String> {
}
