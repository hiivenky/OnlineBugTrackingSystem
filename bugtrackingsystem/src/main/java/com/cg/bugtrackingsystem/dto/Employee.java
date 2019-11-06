package com.cg.bugtrackingsystem.dto;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *author: Venkatesh
 *Description : Employee class 
 *created Date: 05/11/2019
 *last modified : 05/11/2019          
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
@Audited
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public class Employee {
	
	@Id
	@GeneratedValue
	private int employeeId ; 
	private String employeeName;
	private String loginname; 
	private String userPassword;
	private String emailId;
	private String phoneNumber;
	private String roles;
	
	public Employee() {
		super();
	}
	public Employee(int employeeId, String employeeName, String employeeRole, String loginname, String userPassword,
			String emailId, String phoneNumber) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.loginname = loginname;
		this.userPassword = userPassword;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName 
				+ ", loginname=" + loginname + ", userPassword=" + userPassword + ", emailId="
				+ emailId + ", phoneNumber=" + phoneNumber + "]";
	}
}
