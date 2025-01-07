package com.eng.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.eng.entity.Employee;



public interface LoginRepository extends CrudRepository<Employee, Integer> {

public Optional<Employee> findByUsername(String userName); 

}
