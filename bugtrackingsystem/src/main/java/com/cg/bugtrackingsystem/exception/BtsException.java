package com.cg.bugtrackingsystem.exception;

/**
 *author: Venkatesh
 *Description : This Exception class is used to raise all the exceptions
 *              in the application 
 *created Date: 05/11/2019
 *last modified : 05/11/2019            
 */
public class BtsException extends Exception {
	
	public BtsException() {
		super();
	}
	
	public BtsException(String msg) {
		super(msg);
	}

}
