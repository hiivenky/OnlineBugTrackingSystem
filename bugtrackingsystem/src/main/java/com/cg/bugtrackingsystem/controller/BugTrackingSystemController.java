package com.cg.bugtrackingsystem.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bugtrackingsystem.dto.Bug;
import com.cg.bugtrackingsystem.dto.Developer;
import com.cg.bugtrackingsystem.dto.Manager;
import com.cg.bugtrackingsystem.dto.Project;
import com.cg.bugtrackingsystem.dto.SystemUserDetails;
import com.cg.bugtrackingsystem.dto.Ticket;
import com.cg.bugtrackingsystem.repository.ProjectRepository;
import com.cg.bugtrackingsystem.service.EmployeeService;
import com.cg.bugtrackingsystem.service.ManagerService;

/**
 *author: Venkatesh
 *Description : This class acts as a RestController for providing web services 
 *created Date: 06/11/2019
 *last modified : 06/11/2019            
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BugTrackingSystemController {
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the project 
	 *created Date: 06/11/2019
	 *last modified : 06/11/2019     
	 *Input : Manager Object,Authentication Object
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="/manager/addProject")
	public ResponseEntity<String> addProject(@ModelAttribute Project project,@RequestParam("endDate") String projectEndDate,Authentication authentication) {
		Manager manager;
		LocalDate endDate = LocalDate.parse(projectEndDate,DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
			}
			else {
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			project.setManager(manager);
			project.setProjectEndDate(endDate);
			managerService.addProject(project);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Project added successfully",HttpStatus.OK);
	}
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the new registered user 
	 *              to database  
	 *created Date: 06/11/2019
	 *last modified : 06/11/2019     
	 *Input : Manager Object
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="/register")
	public ResponseEntity<String> register(@ModelAttribute Manager manager){
		try {
			managerService.addManager(manager);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Manager added successfully",HttpStatus.OK);
	}
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the a bug to the project 
	 *created Date: 06/11/2019
	 *last modified : 06/11/2019     
	 *Input : Manager Object
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="manager/addBug")
	public ResponseEntity<String> addBug(@ModelAttribute Bug bug,@RequestParam("projectName")String projectName,Authentication authentication){
		Manager manager;
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
				managerService.addBug(bug, projectName);
			}
			else {
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Bug added successfully to the Project",HttpStatus.OK);
	}
	@PostMapping(value="manager/addDeveloper")
	public ResponseEntity<String> raiseTicket(@ModelAttribute Developer developer,Authentication authentication){
		Manager manager;
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
				developer.setManager(manager);
				managerService.addEmployee(developer);
			}
			else {
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Developer added successfully",HttpStatus.OK);
	}
	
	

}
