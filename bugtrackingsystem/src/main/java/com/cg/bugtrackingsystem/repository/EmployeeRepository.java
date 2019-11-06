package com.cg.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtrackingsystem.dto.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
	
	public Employee findByLoginname(String loginname);

}
