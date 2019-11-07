package com.cg.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtrackingsystem.dto.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
	
	public Ticket findByTicketId(Integer ticketId);

}
