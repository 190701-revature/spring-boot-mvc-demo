package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Service layer - Internal business logic, not related to any external
 * service generally
 * 
 * @Service - Another stereotype annotation
 */

@Service
public class HelloService {
	HelloRepository helloRepository;
	
	@Autowired
	public HelloService(HelloRepository helloRepository) {
		this.helloRepository = helloRepository;
	}
	

}
