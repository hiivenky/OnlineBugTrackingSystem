package com.cg.bugtrackingsystem.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *author: Venkatesh
 *Description : Developer class 
 *created Date: 05/11/2019
 *last modified : 05/11/2019          
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
@Audited
public class Developer extends Employee {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticketAssigned",referencedColumnName = "ticketId")
	private Ticket ticketAssigned; 
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn
	private Manager manager;
	private boolean assignStatus;
	
	
	public Developer() {
		super();
	}

	public Developer(Ticket ticketAssigned, Manager manager, boolean assignStatus) {
		super();
		this.ticketAssigned = ticketAssigned;
		this.manager = manager;
		this.assignStatus = assignStatus;
	}

	public Ticket getTicketAssigned() {
		return ticketAssigned;
	}

	public void setTicketAssigned(Ticket ticketAssigned) {
		this.ticketAssigned = ticketAssigned;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public boolean isAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(boolean assignStatus) {
		this.assignStatus = assignStatus;
	}

	@Override
	public String toString() {
		return "Developer [ticketAssigned=" + ticketAssigned + ", manager=" + manager + ", assignStatus=" + assignStatus
				+ ", getEmployeeId()=" + getEmployeeId() + ", getEmployeeName()=" + getEmployeeName()
				 + ", getLoginname()=" + getLoginname()
				+ ", getUserPassword()=" + getUserPassword() + ", getEmailId()=" + getEmailId() + ", getPhoneNumber()="
				+ getPhoneNumber() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
	
	


}
