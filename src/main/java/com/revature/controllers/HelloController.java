package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.services.HelloService;

/*
 * @Controller - Stereotype annotation that tells spring that this is a
 * bean that it can manage, and specifically this bean represents a controller
 * which can be used to handle HTTP requests
 * 
 * @RequestMapping(str) - Configures all endpoints defined by this controller to
 * be initialized with the URL context of str. Such that @Controller("/context")
 * would result in all endpoints being in the form of localhost:{port}/context/etc
 * 
 * @RestController - Configures @Controller and @ResponseBody for all methods.
 */

@RestController
@RequestMapping("hello")
@CrossOrigin(allowedHeaders = "*", methods = {RequestMethod.POST})
public class HelloController {

	private List<String> names = new ArrayList<>();
	private HelloService helloService;
	
	@Autowired
	public HelloController(HelloService helloService) {
		super();
		this.helloService = helloService;
	}

	/**
	 * @ResponseBody - Tells Spring that the returned value does NOT represent a
	 *               template view, but instead is itself the body of the response
	 * @GetMapping - Handles get request with the added string context
	 * @PostMapping
	 * @PutMapping
	 * @DeleteMapping
	 * @PatchMapping
	 * @RequestMapping - (Handle any kind of request)
	 * @return
	 */
	@GetMapping(path = "", produces = "application/json")
	public MyObject sayHello() {
		System.out.println("Hello request received");
		return new MyObject(2, 5);
	}

	@GetMapping(path = "", produces = "text/html")
	public String sayHelloHTML() {
		System.out.println("Hello request received");
		return "<!DOCTYPE html><html><head></head><body><h1>Hello!</h1></body></html>";
	}

	
	/*
	 * @RequestBody - Declares that a parameter is going to be defined
	 * in the HTTP request's body. So create the object from the body data.
	 */
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> saveHello(@RequestBody PersonContainer person) {
		names.add(person.getName());
		
		if (Math.random() > 0.5) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT);
		}
		
		return names.stream()
			.map(name -> "Hello " + name + "!")
			.collect(Collectors.toList());
	}
	
	/*
	 * Define a path variable in the mapping using {identifier}
	 * Map it to a parameter using @PathVariable Type identifier
	 * or if they are a different name using @PathVariable(name="
	 */
	@GetMapping("/{id}")
	public ResponseEntity<String> getHelloWithId(@PathVariable int id) {
		if(id >= names.size()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.ok("Hello " + names.get(id) + "!");
	
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> errorHandler(HttpClientErrorException e) {
		return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
	}
	
}

class PersonContainer {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonContainer(String name) {
		super();
		this.name = name;
	}

	public PersonContainer() {
		super();
		// TODO Auto-generated constructor stub
	}

}

class MyObject {
	int x;
	int y;

	public MyObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public MyObject() {
		super();
	}

}