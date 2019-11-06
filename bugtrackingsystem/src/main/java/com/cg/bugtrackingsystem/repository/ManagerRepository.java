package com.cg.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtrackingsystem.dto.Manager;

public interface ManagerRepository extends JpaRepository<Manager,Integer> {
	
	

}
