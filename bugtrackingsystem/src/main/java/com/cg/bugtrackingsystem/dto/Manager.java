package com.cg.bugtrackingsystem.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *author: Venkatesh
 *Description : Manager class 
 *created Date: 05/11/2019
 *last modified : 05/11/2019          
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
@Audited
public class Manager extends Employee {
	
	@OneToMany(mappedBy = "manager",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Project> projects;
	@OneToMany(mappedBy = "manager",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Developer>	developers;
	@OneToMany(mappedBy = "assignedByManager",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Ticket> tickets;
	@CreatedBy
	protected String createdBy;
	@CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate;
	@LastModifiedBy
	protected String lastModifiedBy;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;
	
	
	public Manager() {
		super();
		projects= new ArrayList<Project>();
		developers = new ArrayList<Developer>();
		tickets = new ArrayList<Ticket>();
	}
	
	
	public Manager(List<Project> projects, List<Developer> developers) {
		super();
		this.projects = projects;
		this.developers = developers;
	}

	public List<Developer> getDevelopers() {
		return developers;
	}

	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
	}


	public List<Project> getProjects() {
		return projects;
	}


	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}


	public List<Ticket> getTickets() {
		return tickets;
	}


	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	

}
