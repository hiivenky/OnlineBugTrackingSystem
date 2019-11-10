package com.cg.bugtrackingsystem.service;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
/**
 *author: Venkatesh
 *Description : interface 
 *created Date: 10/11/2019
 *last modified : 10/11/2019     
 */
public interface DeveloperService {
	public boolean submit();
	public DiagnosticCollector<JavaFileObject> compile(String code);
}
