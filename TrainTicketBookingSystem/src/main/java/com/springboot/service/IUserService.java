package com.springboot.service;

import java.util.List;

import com.springboot.dto.request.LoginRequest;
import com.springboot.dto.request.UserRequest;
import com.springboot.dto.response.UserResponse;

public interface IUserService {
  public UserResponse registerUser(UserRequest userReq);
  
  public UserResponse findbyId(Long id);
  
  public List<UserResponse> getAllUsers();
  
  public String login(LoginRequest loginRequest);
}
