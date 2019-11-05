package com.cg.bugtrackingsystem.dto;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 *Description : Ticket class 
 *created Date: 05/11/2019
 *last modified : 05/11/2019          
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
@Audited
public class Ticket {
	
	@Id
	@GeneratedValue
	private int ticketId;
	private String ticketNote;
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "ticketAssigned")
	private Developer assignedToEmployee; 
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
	@JoinColumn
	private Employee assignedByManager;
	private String ticketStatus;
	private String codeSnippet;
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "ticket")
	private Bug bug;
	@Enumerated(EnumType.STRING)
	private CriticalLevel ticketCriticalLevel;
	private LocalDateTime ticketDeadline;
	
	public Ticket() {
		
	}

	public Ticket(int ticketId, String ticketNote, Developer assignedToEmployee, Employee assignedByManager,
			String ticketStatus, String codeSnippet, CriticalLevel ticketCriticalLevel, LocalDateTime ticketDeadline) {
		super();
		this.ticketId = ticketId;
		this.ticketNote = ticketNote;
		this.assignedToEmployee = assignedToEmployee;
		this.assignedByManager = assignedByManager;
		this.ticketStatus = ticketStatus;
		this.codeSnippet = codeSnippet;
		this.ticketCriticalLevel = ticketCriticalLevel;
		this.ticketDeadline = ticketDeadline;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(String ticketNote) {
		this.ticketNote = ticketNote;
	}

	public Developer getAssignedToEmployee() {
		return assignedToEmployee;
	}

	public void setAssignedToEmployee(Developer assignedToEmployee) {
		this.assignedToEmployee = assignedToEmployee;
	}

	public Employee getAssignedByManager() {
		return assignedByManager;
	}

	public void setAssignedByManager(Employee assignedByManager) {
		this.assignedByManager = assignedByManager;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getCodeSnippet() {
		return codeSnippet;
	}

	public void setCodeSnippet(String codeSnippet) {
		this.codeSnippet = codeSnippet;
	}

	public CriticalLevel getTicketCriticalLevel() {
		return ticketCriticalLevel;
	}

	public void setTicketCriticalLevel(CriticalLevel ticketCriticalLevel) {
		this.ticketCriticalLevel = ticketCriticalLevel;
	}

	public LocalDateTime getTicketDeadline() {
		return ticketDeadline;
	}

	public void setTicketDeadline(LocalDateTime ticketDeadline) {
		this.ticketDeadline = ticketDeadline;
	}
	
	

	public Bug getBug() {
		return bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", ticketNote=" + ticketNote + ", assignedToEmployee="
				+ assignedToEmployee + ", assignedByManager=" + assignedByManager + ", ticketStatus=" + ticketStatus
				+ ", codeSnippet=" + codeSnippet + ", ticketCriticalLevel=" + ticketCriticalLevel + ", ticketDeadline="
				+ ticketDeadline + "]";
	}
	


}
