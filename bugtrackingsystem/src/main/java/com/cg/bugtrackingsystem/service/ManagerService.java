package com.cg.bugtrackingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.bugtrackingsystem.dto.Bug;
import com.cg.bugtrackingsystem.dto.Developer;
import com.cg.bugtrackingsystem.dto.Employee;
import com.cg.bugtrackingsystem.dto.Manager;
import com.cg.bugtrackingsystem.dto.Project;
import com.cg.bugtrackingsystem.dto.Ticket;

/**
*author: Venkatesh
*Description : Manager Service Class
*created Date: 06/11/2019
*last modified : 06/11/2019            
*/

public interface ManagerService  {
	
	public Project addProject(Project project) throws Exception;
	public Project endProject(Integer projectId);
	public Bug addBug(Bug bug,String projectName) throws Exception;
	public Bug endBug(Integer bugId) throws Exception;
	public Ticket raiseTicket(Ticket ticket,Integer developerId,Integer bugId) throws Exception;
	public Ticket deleteTicket(Integer ticketId) throws Exception;
	public Employee addEmployee(Developer employee) throws Exception;
	public Manager addManager(Manager manager) throws Exception;
	public List<Project> getProjects(Integer managerId);
	public List<Developer> getDevelopers(Integer managerId)throws Exception;

}
