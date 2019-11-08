package com.cg.bugtrackingsystem.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bugtrackingsystem.dto.Bug;
import com.cg.bugtrackingsystem.dto.CriticalLevel;
import com.cg.bugtrackingsystem.dto.Developer;
import com.cg.bugtrackingsystem.dto.Employee;
import com.cg.bugtrackingsystem.dto.Manager;
import com.cg.bugtrackingsystem.dto.Project;
import com.cg.bugtrackingsystem.dto.SystemUserDetails;
import com.cg.bugtrackingsystem.dto.Ticket;
import com.cg.bugtrackingsystem.repository.ProjectRepository;
import com.cg.bugtrackingsystem.service.EmployeeService;
import com.cg.bugtrackingsystem.service.ManagerService;
import com.cg.onlinewalletwithspringbootrest.model.JwtResponse;

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
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the a bug to the project 
	 *created Date: 06/11/2019
	 *last modified : 06/11/2019     
	 *Input : Manager Object
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="manager/addDeveloper")
	public ResponseEntity<String> addDeveloper(@ModelAttribute Developer developer,Authentication authentication){
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
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the a bug to the project 
	 *created Date: 06/11/2019
	 *last modified : 07/11/2019     
	 *Input : Manager Object
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="manager/raiseTicket")
	public ResponseEntity<String> raiseTicket(@ModelAttribute Ticket ticket,@RequestParam("deadLine")String deadLine,
			@RequestParam("bugId")Integer bugId,@RequestParam("developerId")Integer developerId,Authentication authentication){
		Manager manager;
		boolean present = false;
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
				ticket.setAssignedByManager(manager);
				ticket.setTicketDeadline(LocalDateTime.parse(deadLine,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
				List<Project> projects = manager.getProjects();
				if(projects==null) {
					return new ResponseEntity<String>("Projects not present create project first",HttpStatus.INTERNAL_SERVER_ERROR); 
				}
				
				List<Developer> developers = manager.getDevelopers();
				if(developers!=null) {
					for(int i=0;i<developers.size();i++) {
						if(developers.get(i).getEmployeeId()==developerId) {
							present =true;
							break;
						}
					}
					if(present) {
						managerService.raiseTicket(ticket, developerId, bugId);
					}
					else {
						return new ResponseEntity<String>("Developer not present",HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				else {
					return new ResponseEntity<String>("No developers present",HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}
			else {
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Ticket Added Successfully ",HttpStatus.OK);
	}
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the a bug to the project 
	 *created Date: 06/11/2019
	 *last modified : 08/11/2019     
	 *Input : Manager Object
	 *Output : ResponseEntity         
	 */
	@GetMapping(value="/getRole")
	public ResponseEntity<?> validateLogin(@RequestParam("loginName") String loginname) {
		Employee employee=employeeService.getUser(loginname);
		if(employee==null) {
			System.out.println("Inside error");
			return new ResponseEntity<String>("Employee not present",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println(employee.getRoles());
		return ResponseEntity.ok(new JwtResponse(employee.getRoles()));
	}
	
	

}
