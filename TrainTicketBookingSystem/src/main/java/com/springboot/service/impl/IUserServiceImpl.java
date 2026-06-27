package com.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.dto.request.LoginRequest;
import com.springboot.dto.request.UserRequest;
import com.springboot.dto.response.UserResponse;
import com.springboot.entity.User;
import com.springboot.enums.Role;
import com.springboot.exception.UserNotFound;
import com.springboot.repository.UserRepository;
import com.springboot.service.IUserService;
import com.springboot.service.JwtService;
@Service
public class IUserServiceImpl implements IUserService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Autowired
	    private AuthenticationManager authenticationManager;  // ← here

	    @Autowired
	    private JwtService jwtService;

	 
	@Override
    public UserResponse registerUser(UserRequest userReq) {

        // create new User object
        User user = new User();
        user.setName(userReq.getName());
        user.setEmail(userReq.getEmail());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        user.setPhone(userReq.getPhone());
        user.setRole(Role.USER);

        // save and get saved user with ID
        User savedUser = repo.save(user);

        // create new UserResponse object
        UserResponse userResp = new UserResponse();
        userResp.setId(savedUser.getId());
        userResp.setName(savedUser.getName());
        userResp.setEmail(savedUser.getEmail());
        userResp.setPhone(savedUser.getPhone());
        userResp.setRole(savedUser.getRole());

        return userResp;
    }


	@Override
	public UserResponse findbyId(Long id) {
		  User user = repo.findById(id).orElseThrow(()-> new UserNotFound("No user present with this id : " +id+" "));
		  
		  UserResponse userRep = new UserResponse();
		  userRep.setId(user.getId());
		  userRep.setName(user.getName());
		  userRep.setEmail(user.getEmail());
		  userRep.setPhone(user.getPhone());
		  userRep.setRole(user.getRole());
		return userRep;
	}


	@Override
	public List<UserResponse> getAllUsers() {
	 List<User> users = repo.findAll();
	 if(users.isEmpty()) {
//		   throw new UserNotFound("No users found");
		 return null;
	 }
	 
	 List<UserResponse>useReps= new ArrayList<>();
	 for(User u : users) {
		 UserResponse usp= new UserResponse();
		 usp.setName(u.getName());
		 usp.setEmail(u.getEmail());
		 usp.setId(u.getId());
		 usp.setPhone(u.getPhone());
		 usp.setRole(u.getRole());
		 useReps.add(usp);
		 
	 }
	 
		 
	 
	 
		return useReps;
	}
	
	
	
	public String login(LoginRequest loginRequest) {

	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    loginRequest.getEmail(),
	                    loginRequest.getPassword()
	            )
	    );

	    return jwtService.generateTokenFromEmail(loginRequest.getEmail());
	}
	
	
	
	


 

}
