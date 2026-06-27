package com.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.request.LoginRequest;
import com.springboot.dto.request.UserRequest;
import com.springboot.dto.response.UserResponse;
import com.springboot.service.IUserService;
import com.springboot.service.impl.UserDetailsServiceImpl;
import com.springboot.util.ResponseStructure;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@Autowired
	private UserDetailsServiceImpl userService;
	@PostMapping("/register-user")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userreq){
		

	    UserResponse userResponse = service.registerUser(userreq);

	    ResponseStructure<UserResponse> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.CREATED.value());
	    structure.setMessage("User registered successfully");
	    structure.setData(userResponse);
	    structure.setDate(LocalDateTime.now());
	    

	    return new ResponseEntity<>(structure, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getByuser-id/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>>getByUserId(@PathVariable Long id){
		
		 UserResponse userResponse = service. findbyId(id);

		    ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		    structure.setStatus(HttpStatus. OK.value());
		    structure.setMessage("user with the id :" + id+" ");
		    structure.setData(userResponse);
		    structure.setDate(LocalDateTime.now());
		    
		    return new ResponseEntity<>(structure, HttpStatus.OK);
		    
		
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<ResponseStructure<List<UserResponse>>>getAllUsers(){
		
		   List<UserResponse> userResponse = service.getAllUsers();

		    ResponseStructure<List<UserResponse>> structure = new ResponseStructure<>();
		    structure.setStatus(HttpStatus. OK.value());
		    structure.setMessage("AllUsers from the dataBase");
		    structure.setData(userResponse);
		    structure.setDate(LocalDateTime.now());
		    
		    return new ResponseEntity<ResponseStructure<List<UserResponse>>>(structure, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<String>> login(@RequestBody LoginRequest loginRequest) {
	   String token = service.login(loginRequest);

	    ResponseStructure<String> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("Login successful");
	    structure.setData(token);
	    structure.setDate(LocalDateTime.now());

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}

}
