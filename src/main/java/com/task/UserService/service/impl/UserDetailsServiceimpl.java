//package com.task.UserService.service.impl;
//
//import com.task.UserService.model.User;
//import com.task.UserService.repository.UserRepository;
//import com.task.UserService.vo.UserPrincipal;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserDetailsServiceimpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = repository.findByEmail(username);
//        if (user == null) {
//          throw new UsernameNotFoundException("User not found");
//        }
//
//        return new UserPrincipal(user);
//    }
//}
