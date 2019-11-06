package com.cg.bugtrackingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtrackingsystem.dto.Project;


public interface ProjectRepository extends JpaRepository<Project,Integer> {
	public Project findByProjectId(Integer projectId);
	public Project findByProjectName(String projectName);
}
