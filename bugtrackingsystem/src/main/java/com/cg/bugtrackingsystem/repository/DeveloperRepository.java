package com.cg.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtrackingsystem.dto.Developer;

public interface DeveloperRepository extends JpaRepository<Developer,Integer> {
	
	public Developer findByEmployeeId(Integer employeeId);
}
