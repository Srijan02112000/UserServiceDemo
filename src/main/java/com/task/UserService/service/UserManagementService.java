package com.task.UserService.service;

import com.task.UserService.model.User;
import com.task.UserService.vo.UserRequestVo;
import com.task.UserService.vo.UserResponseVo;

import java.util.List;


public interface UserManagementService {

    public UserResponseVo registerUser(UserRequestVo user);

    public boolean validateUser(String email, String password);

    public List<UserResponseVo> getAllUsers();

    public void deleteUser(String email);
}
