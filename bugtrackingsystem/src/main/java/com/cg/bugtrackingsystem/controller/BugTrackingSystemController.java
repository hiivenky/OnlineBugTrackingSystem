package com.cg.bugtrackingsystem.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.cg.bugtrackingsystem.service.DeveloperService;
import com.cg.bugtrackingsystem.service.EmployeeService;
import com.cg.bugtrackingsystem.service.ManagerService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sun.net.httpserver.HttpsServer;
import com.cg.bugtrackingsystem.model.JwtResponse;

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
	@Autowired
	private DeveloperService developerService;
	private static final Logger logger = LoggerFactory.getLogger(BugTrackingSystemController.class);
	
	/**
	 *author: Venkatesh
	 *Description : This method is used for adding the project 
	 *created Date: 06/11/2019
	 *last modified : 06/11/2019     
	 *Input : Manager Object,Authentication Object
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="/manager/addProject")
	public ResponseEntity<?> addProject(@ModelAttribute Project project,@RequestParam("endDate") String projectEndDate,Authentication authentication) {
		Manager manager;
		LocalDate endDate = LocalDate.parse(projectEndDate,DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
		System.out.println("inside add project");
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
				logger.info("manager "+manager.getLoginname()+" adding project");
				System.out.println(manager.getEmailId());
			}
			else {
				logger.error("error while adding project");
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			project.setManager(manager);
			project.setProjectEndDate(endDate);
			managerService.addProject(project);
			logger.trace("manager "+manager.getLoginname()+" added Project "+project.getProjectName());
		} catch (Exception e) {
			logger.error("error in adding project");
			System.out.println(e.getMessage());
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
			logger.trace("inside register");
			managerService.addManager(manager);
		} catch (Exception e) {
			logger.error("error in registration");
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
		System.out.println(projectName);
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
				managerService.addBug(bug, projectName);
				logger.trace("manager "+manager.getLoginname()+" added bug "+bug.getBugDescription());
			}
			else {
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("error in adding bug");
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
				Email from = new Email("test@example.com");
			    String subject = "Sending with SendGrid is Fun";
			    Email to = new Email("test@example.com");
			    Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
			    Mail mail = new Mail(from, subject, to, content);
			    System.out.println(System.getenv("SENDGRID_API_KEY"));
			    SendGrid sg = new SendGrid("SENDGRID_API_KEY");
			    Request request = new Request();
			    try {
			      request.setMethod(Method.POST);
			      request.setEndpoint("mail/send");
			      request.setBody(mail.build());
			      Response response = sg.api(request);
			      System.out.println(response.getStatusCode());
			      System.out.println(response.getBody());
			      System.out.println(response.getHeaders());
			      System.out.println("sent");
			    } catch (IOException ex) {
			    	System.out.println(ex.getMessage());
			      throw ex;
			    }
				logger.trace("manager "+manager.getLoginname()+" added developer "+developer.getLoginname());
			}
			else {
				logger.error("error in adding developer");
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
		System.out.println("Inside raise ticket");
		Manager manager;
		boolean present = false;
		boolean bugPresent=false;
		try {
			if(authentication.isAuthenticated()) {
				SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
				manager = (Manager)employeeService.getUser(userDetails.getUsername());
				ticket.setAssignedByManager(manager);
				deadLine=deadLine.replace("T", " ");
				ticket.setTicketDeadline(LocalDateTime.parse(deadLine,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
				List<Project> projects = manager.getProjects();
				if(projects==null) {
					return new ResponseEntity<String>("Projects not present create project first",HttpStatus.INTERNAL_SERVER_ERROR); 
				}
				for(int i=0;i<projects.size();i++) {
					List<Bug> bugs = projects.get(i).getBugs();
					for(int j=0;j<bugs.size();j++) {
						if(bugs.get(j).getBugId()==bugId) {
							bugPresent=true;
							break;
						}
					}
				}
				if(!bugPresent) {
					logger.trace("wrong bug id entered by manager "+manager.getLoginname());
					return new ResponseEntity<String>("Invalid bug Id",HttpStatus.INTERNAL_SERVER_ERROR);
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
						logger.trace("ticket "+ticket.getTicketId()+" raised by manager "+manager.getLoginname()+
								" to developer "+developerId);
					}
					else {
						logger.error("while raising ticket");
						return new ResponseEntity<String>("Developer not present",HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				else {
					logger.error("error while raising ticket");
					return new ResponseEntity<String>("No developers present",HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}
			else {
				logger.error("error while raising ticket");
				return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("error while raising ticket");
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
	/**
	 *author: Venkatesh
	 *Description : This method is used for getting all the projects 
	 *created Date: 10/11/2019
	 *last modified : 10/11/2019     
	 *Input : Manager Object
	 *Output : ResponseEntity         
	 */
	@GetMapping(value="/getProjects")
	public ResponseEntity<?> getProjects(Authentication authentication){
		Manager manager;
		if(authentication.isAuthenticated()) {
			SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
			manager = (Manager)employeeService.getUser(userDetails.getUsername());
			if(manager!=null) {
				List<Project> ret = new ArrayList<Project>();
				List<Project> projects=manager.getProjects();
				for(int i=0;i<projects.size();i++) {
					ret.add(projects.get(i));
					ret.get(i).setBugs(null);
					ret.get(i).setManager(null);
				}
				
				logger.trace("projects are viewed by "+manager.getLoginname());
				return new ResponseEntity<List<Project>>(ret,HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 *author: Venkatesh
	 *Description : This method is used for getting all the developers 
	 *created Date: 10/11/2019
	 *last modified : 10/11/2019     
	 *Input : Authentication Object
	 *Output : ResponseEntity         
	 */
	@GetMapping(value="/getDevelopers")
	public ResponseEntity<?> getDevelopers(Authentication authentication){
		Manager manager;
		if(authentication.isAuthenticated()) {
			SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
			manager = (Manager)employeeService.getUser(userDetails.getUsername());
			if(manager!=null) {
				List<Developer> ret = new ArrayList<Developer>();
				List<Developer> developers=manager.getDevelopers();
				for(int i=0;i<developers.size();i++) {
					ret.add(developers.get(i));
					ret.get(i).setTicketAssigned(null);
					ret.get(i).setManager(null);
				}
				logger.trace("developers are viewed by "+manager.getLoginname());
				return new ResponseEntity<List<Developer>>(ret,HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 *author: Venkatesh
	 *Description : This method is used for compilation 
	 *created Date: 10/11/2019
	 *last modified : 10/11/2019     
	 *Input : String
	 *Output : ResponseEntity         
	 */
	@PostMapping(value="/compile")
	public ResponseEntity<?> compile(@RequestParam("code") String code){
		
		if(code!=null) {
			try {
				List<String> errors=developerService.compile(code);
				logger.trace("compiling ......");
				if(errors!=null) {
					return new ResponseEntity<List<String>>(errors,HttpStatus.OK);
				}
				else {
					return new ResponseEntity<String>("compilation successfull",HttpStatus.OK);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
				return new ResponseEntity<String>("compiler error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("please type the code",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	@GetMapping(value="/getTicket")
	public ResponseEntity<?> getTicket(Authentication authentication){
		Developer developer;
		if(authentication.isAuthenticated()) {
			SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
			developer = (Developer)employeeService.getUser(userDetails.getUsername());
			if(developer.isAssignStatus()) {
				return  ResponseEntity.ok(new JwtResponse(developer.getTicketAssigned().getCodeSnippet()));
			}
			else {
				return new ResponseEntity<String>("",HttpStatus.OK);
			}
		}
		else {
			return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@PostMapping(value="/submit")
	public ResponseEntity<?> submit(@RequestParam("finalCode") String code ,Authentication authentication){
		Developer developer;
		System.out.println("inside submit controller");
		if(authentication.isAuthenticated()) {
			SystemUserDetails userDetails = (SystemUserDetails)authentication.getPrincipal();
			developer = (Developer)employeeService.getUser(userDetails.getUsername());
			Ticket ticketAssigned = developer.getTicketAssigned();
			int id=ticketAssigned.getBug().getBugId();
			developerService.submit(id,developer.getEmployeeId(),code);
			return new ResponseEntity<String>("code submitted successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("please login",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

}
