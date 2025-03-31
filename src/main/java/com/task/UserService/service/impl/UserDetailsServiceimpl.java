package com.task.UserService.service.impl;

import com.task.UserService.model.User;
import com.task.UserService.repository.UserRepository;
import com.task.UserService.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

    public UserDetailsServiceimpl(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private  UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = repository.findByEmail(name);
        if (user == null) {
          throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole()))

        );
//        UserPrincipal Usernew = UserPrincipal.builder()
//              .name(user.getName())
//              .password(user.getPassword())
//              .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
//              .build();

//        return Usernew;
    }
}
