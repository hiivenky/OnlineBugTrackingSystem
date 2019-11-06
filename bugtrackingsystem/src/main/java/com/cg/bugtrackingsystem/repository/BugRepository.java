package com.cg.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtrackingsystem.dto.Bug;

public interface BugRepository extends JpaRepository<Bug,Integer> {

}
