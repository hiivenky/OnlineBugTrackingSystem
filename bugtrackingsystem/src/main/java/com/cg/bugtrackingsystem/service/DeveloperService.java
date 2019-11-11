package com.cg.bugtrackingsystem.service;

import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
/**
 *author: Venkatesh
 *Description : interface 
 *created Date: 10/11/2019
 *last modified : 10/11/2019     
 */
public interface DeveloperService {
	public boolean submit(int bugId,int developerId,String code);
	public List<String> compile(String code);
}
