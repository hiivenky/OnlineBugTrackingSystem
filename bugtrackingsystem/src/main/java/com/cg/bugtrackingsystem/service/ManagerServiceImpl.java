package com.cg.bugtrackingsystem.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.bugtrackingsystem.dto.Bug;
import com.cg.bugtrackingsystem.dto.Developer;
import com.cg.bugtrackingsystem.dto.Employee;
import com.cg.bugtrackingsystem.dto.Manager;
import com.cg.bugtrackingsystem.dto.Project;
import com.cg.bugtrackingsystem.dto.Ticket;
import com.cg.bugtrackingsystem.exception.BtsException;
import com.cg.bugtrackingsystem.repository.BugRepository;
import com.cg.bugtrackingsystem.repository.DeveloperRepository;
import com.cg.bugtrackingsystem.repository.EmployeeRepository;
import com.cg.bugtrackingsystem.repository.ManagerRepository;
import com.cg.bugtrackingsystem.repository.ProjectRepository;
import com.cg.bugtrackingsystem.repository.TicketRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
*author: Venkatesh
*Description : Manager Service Class Implementation
*created Date: 06/11/2019
*last modified : 06/11/2019            
*/
@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private ProjectRepository projectDao;
	@Autowired
	private ManagerRepository managerDao;
	@Autowired
	private BugRepository bugDao;
	@Autowired
	private EmployeeRepository employeeDao;
	@Autowired
	private DeveloperRepository developerDao;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private TicketRepository ticketDao;
	
	/**
	*author: Venkatesh
	*Description : this function is used to add project
	*created Date: 06/11/2019
	*last modified : 06/11/2019
	*Input : Project Object
	*Output : Project Object            
	 * @throws BtsException 
	*/
	@Override
	public Project addProject(Project project) throws BtsException {
		if(project==null) {
			throw new BtsException("Project can not be null");
		}
		List<Project> projects = projectDao.findAll();
		if(!projects.isEmpty()) {
		
		for(int i=0;i<projects.size();i++) {
			if(projects.get(i).getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) {
				throw new BtsException("project already exists");
			}
		}
		if(project.getProjectCost()<=100) {
			System.out.println("cost excep");
			throw new BtsException("project cost can not be less than 100");
		}
		if(project.getProjectEndDate().compareTo(LocalDate.now())<0) {
			System.out.println("inside end date");
			throw new BtsException("Invalid Project End Date");
		}
		}
		System.out.println("outside all");
		projectDao.save(project);
		return project;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to add Manager
	*created Date: 06/11/2019
	*last modified : 06/11/2019
	*Input : Project Object
	*Output : Project Object            
	 * @throws BtsException 
	*/
	@Override
	public Manager addManager(Manager manager) throws BtsException {
		if(manager==null) {
			throw new BtsException("manager can not be null");
		}
		if(!manager.getEmployeeName().matches("[a-zA-Z]+")) {
			throw new BtsException("Name must have only alphabets");
		}
		if(!manager.getPhoneNumber().matches("\\d{10}")) {
			throw new BtsException("phone number must have 10 digits");
		}
		if(!manager.getEmailId().matches(".{4,30}@[a-z]{3,7}\\.com")) {
			throw new BtsException("please enter a valid mailId");
		}
		if(!manager.getUserPassword().matches("\\d{8,}")) {
			throw new BtsException("Passord must have minimum 8 charecters");
		}
		List<Employee> employees = employeeDao.findAll();
		if(employees!=null) {
			for(int i=0;i<employees.size();i++) {
				if(employees.get(i).getPhoneNumber().equals(manager.getPhoneNumber())){
					throw new BtsException("Phone Number already exists in the database");
				}
			}
			for(int i=0;i<employees.size();i++) {
				if(employees.get(i).getEmailId().equals(manager.getEmailId())) {
					throw new BtsException("emailId already exists in the database");
				}
			}
		}
		manager.setRoles("ROLE_ADMIN");
		manager.setUserPassword(bcryptEncoder.encode(manager.getUserPassword()));
		managerDao.save(manager);
		manager.setLoginname(manager.getEmployeeName()+manager.getEmployeeId());
		managerDao.save(manager);
		return manager;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to add project
	*created Date: 06/11/2019
	*last modified : 07/11/2019
	*Input : Integer projectId
	*Output : Project Object            
	 * @throws BtsException 
	*/
	@Override
	public Project endProject(Integer projectId) {
		return null;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to add Bug
	*created Date: 06/11/2019
	*last modified : 07/11/2019
	*Input : Bug Object
	*Output : Bug Object            
	 * @throws BtsException 
	*/
	@Override
	public Bug addBug(Bug bug,String projectName) throws BtsException {
		if(bug==null) {
			throw new BtsException("bug can not be null");
		}
		Project project = projectDao.findByProjectName(projectName);
		if(project==null) {
			throw new BtsException("project not present");
		}
		bug.setProject(project);
		bugDao.save(bug);
		return bug;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to end bug
	*created Date: 06/11/2019
	*last modified : 06/11/2019
	*Input : Bug Object
	*Output : Bug Object            
	 * @throws BtsException 
	*/
	@Override
	public Bug endBug(Integer bugId) {
		
		return null;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to raise ticket
	*created Date: 06/11/2019
	*last modified : 07/11/2019
	*Input : Ticket Object
	*Output : Ticket Object            
	 * @throws BtsException 
	*/
	@Override
	public Ticket raiseTicket(Ticket ticket,Integer developerId,Integer bugId) throws BtsException {
		Developer developer = developerDao.findByEmployeeId(developerId);
		if(developer==null) {
			throw new BtsException("Invalid Id entered");
		}
		if(developer.isAssignStatus()) {
			throw new BtsException("Ticket is already assigned to the developer");
		}
		developer.setAssignStatus(true);
		Bug bug = bugDao.findBybugId(bugId);
		if(bug==null) {
			throw new BtsException("Invalid bug Id entered");
		}
		if(ticket.getTicketDeadline().compareTo(LocalDateTime.now())<0) {
			throw new BtsException("Ticket Deadline entered is invalid");
		}
		if(bug.getBugStatus().equals("notAssigned")) {
			bug.setBugStatus("Assigned");
			ticket.setAssignedToEmployee(developer);
			ticket.setTicketStatus("Assigned");
			bug.setTicket(ticket);
			developer.setTicketAssigned(ticket);
			bugDao.save(bug);
			developerDao.save(developer);
			Email from = new Email("venkymullagiri@gmail.com");
		    String subject = "Ticket has been assigned review the bug as early as possible";
		    Email to = new Email(developer.getEmailId());
		    Content content = new Content("text/plain", "ticket has been assigned by "+developer.getManager().getLoginname()
		    		+"bug id is ");
		    Mail mail = new Mail(from, subject, to, content);
		    System.out.println(System.getenv("SENDGRID_API_KEY"));
		    SendGrid sg = new SendGrid("");
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
		    }
		}
		else {
			throw new BtsException("Bug is already assigned to a developer");
		}
		return ticket;
	}

	@Override
	public Ticket deleteTicket(Integer ticketId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to raise ticket
	*created Date: 06/11/2019
	*last modified : 07/11/2019
	*Input : Employee Object
	*Output : Employee Object            
	 * @throws BtsException 
	*/
	@Override
	public Employee addEmployee(Developer employee) throws BtsException {
		if(employee==null) {
			throw new BtsException("manager can not be null");
		}
		if(!employee.getEmployeeName().matches("[a-zA-Z]+")) {
			throw new BtsException("Name must have only alphabets");
		}
		if(!employee.getPhoneNumber().matches("\\d{10}")) {
			throw new BtsException("phone number must have 10 digits");
		}
		if(!employee.getEmailId().matches(".{4,30}@[a-z]{3,7}\\.com")) {
			throw new BtsException("please enter a valid mailId");
		}
		if(!employee.getUserPassword().matches("\\d{8,}")) {
			throw new BtsException("Passord must have minimum 8 charecters");
		}
		List<Employee> employees = employeeDao.findAll();
		if(employees!=null) {
			for(int i=0;i<employees.size();i++) {
				if(employees.get(i).getPhoneNumber().equals(employee.getPhoneNumber())){
					throw new BtsException("Phone Number already exists in the database");
				}
			}
			for(int i=0;i<employees.size();i++) {
				if(employees.get(i).getEmailId().equals(employee.getEmailId())) {
					throw new BtsException("emailId already exists in the database");
				}
			}
		}
		employee.setRoles("ROLE_CUSTOMER");
		employee.setUserPassword(bcryptEncoder.encode(employee.getUserPassword()));
		developerDao.save(employee);
		employee.setLoginname(employee.getEmployeeName()+employee.getEmployeeId());
		developerDao.save(employee);
		Email from = new Email("venkymullagiri@gmail.com");
	    String subject = "Sending with SendGrid is Fun";
	    Email to = new Email(employee.getEmailId());
	    Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
	    Mail mail = new Mail(from, subject, to, content);
	    System.out.println(System.getenv("SENDGRID_API_KEY"));
	    SendGrid sg = new SendGrid("");
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
	    }
		return employee;
	}
	
	@Override
	public List<Project> getProjects(Integer managerId) {
		return null;
	}
	/**
	*author: Venkatesh
	*Description : this function is used to raise ticket
	*created Date: 07/11/2019
	*last modified : 07/11/2019
	*Input : Ticket Object
	*Output : List<Developer>            
	 * @throws BtsException 
	 * @throws IOException 
	*/
	@Override
	public List<Developer> getDevelopers(Integer managerId) throws BtsException, IOException {
		
		Manager manager = managerDao.findByEmployeeId(managerId);
		if(manager==null) {
			throw new BtsException("Invalid manager Id");
		}
		List<Developer> developers = manager.getDevelopers();
		if(developers==null||developers.size()==0) {
			throw new BtsException("No developer present");
		}
		return developers;
	}

}
