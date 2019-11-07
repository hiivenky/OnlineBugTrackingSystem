package com.cg.bugtrackingsystem.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *author: Venkatesh
 *Description : Bug class 
 *created Date: 05/11/2019
 *last modified : 05/11/2019          
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
@Audited
public class Bug {
	@Id
	@GeneratedValue
	private int bugId;
	private String bugDescription;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticket",referencedColumnName = "ticketId")
	Ticket ticket;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
	@JoinColumn
	Project project;
	private String bugStatus;
	
	public Bug() {
		this.ticket=null;
		bugStatus="notAssigned";
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getBugId() {
		return bugId;
	}

	public void setBugId(int bugId) {
		this.bugId = bugId;
	}

	public String getBugDescription() {
		return bugDescription;
	}

	public void setBugDescription(String bugDescription) {
		this.bugDescription = bugDescription;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}
	
	
}
