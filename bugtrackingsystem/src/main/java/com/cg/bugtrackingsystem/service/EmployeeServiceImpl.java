package com.cg.bugtrackingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bugtrackingsystem.dto.Employee;
import com.cg.bugtrackingsystem.repository.EmployeeRepository;

@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeDao;

	@Override
	public Employee getUser(String loginName) {
		Employee employee = employeeDao.findByLoginname(loginName); 
		if(employee !=null) {
			System.out.println(employee.getPhoneNumber());
			return employee;
		}
		return null;
	}

}
