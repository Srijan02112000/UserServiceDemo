package com.task.UserService.service.impl;

import com.task.UserService.model.User;
import com.task.UserService.repository.UserRepository;
import com.task.UserService.service.UserManagementService;
import com.task.UserService.util.CustomExceptionForUser;
import com.task.UserService.util.UserNotFoundException;
import com.task.UserService.vo.UserRequestVo;
import com.task.UserService.vo.UserResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl  implements UserManagementService {

    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RestTemplate restTemplate;

    public UserManagementServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder ,RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.restTemplate = restTemplate;
    }


    public UserResponseVo registerUser(UserRequestVo userReq) {
        if (userRepository.existsByEmail(userReq.getEmail())) {
            throw new CustomExceptionForUser("Email already exists! Please use a different email.");
        }
        User user = setUserdetails(userReq);
        userRepository.save(user);
       return mapToUserResponseVo(user);
    }
    public User setUserdetails(UserRequestVo userReq) {
        User user = new User();
        user.setEmail(userReq.getEmail());
        user.setGender(userReq.getGender());
        user.setName(userReq.getName()); user.setRole(userReq.getRole() != null ? userReq.getRole() : "USER");
       user.setPassword(bCryptPasswordEncoder.encode(userReq.getPassword()));
//        user.setPassword(userReq.getPassword());
        String ip = restTemplate.getForObject("https://api.ipify.org?format=json", Map.class).get("ip").toString();
        user.setIpAddress(ip);
        String url = "http://ip-api.com/json/" + ip;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        user.setCountry(response.get("country").toString());
        return user;
    }
   public UserResponseVo mapToUserResponseVo(User user) {
    return UserResponseVo.builder()
          .name(user.getName())
          .email(user.getEmail())
          .country(user.getCountry())
            .role(user.getRole())
          .ipAddress(user.getIpAddress())
          .gender(user.getGender())
          .build();
    }

    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return Objects.nonNull(user) && bCryptPasswordEncoder.matches(password,user.getPassword());
    }

    public List<UserResponseVo> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(user -> new UserResponseVo(
                        user.getName(),
                        user.getEmail(),
                        user.getGender(),
                        user.getRole(),
                        user.getIpAddress(),
                        user.getCountry()
                ))
                .collect(Collectors.toList());
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException("User Not Found");
        }
        userRepository.delete(user);
    }
}
