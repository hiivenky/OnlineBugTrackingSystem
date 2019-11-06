package com.cg.onlinewalletwithspringbootrest.model;

import java.io.Serializable;
/**
 *author: Venkatesh  
 *description : this class is used to store jwt response
 *created Date: 08/10/2019
 *last modified : 11/10/2019     
 */

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}