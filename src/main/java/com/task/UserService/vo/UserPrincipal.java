//package com.task.UserService.vo;
//
//import org.springframework.security.core.GrantedAuthority;
//import com.task.UserService.model.User;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//public class UserPrincipal implements UserDetails {
//
//    private String name;
//    private String password;
//    private List<GrantedAuthority> authorities;
//
//    public UserPrincipal(User user) {
//        this.name = user.getName();
//        this.password = user.getPassword();
//        this.authorities = List.of(new SimpleGrantedAuthority( user.getRole()));
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.name;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
