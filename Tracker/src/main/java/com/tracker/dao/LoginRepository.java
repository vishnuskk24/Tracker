package com.tracker.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tracker.entity.Employee;



public interface LoginRepository extends CrudRepository<Employee, Integer> {

public Optional<Employee> findByUsername(String userName); 

}
