package com.task.UserService.config;


import com.task.UserService.service.impl.UserDetailsServiceimpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

//import static org.springframework.security.config.Customizer.withDefaults;

//import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceimpl userDetailsService;

    public SecurityConfig(UserDetailsServiceimpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/delete").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/all").hasRole("ADMIN")
                        .requestMatchers(
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/actuator/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


        @Bean
       public UserDetailsService userDetailsService() {
        log.info("Creating user details service");
        InMemoryUserDetailsManager in = new InMemoryUserDetailsManager();
        in.createUser(User.withUsername("admin@gmail.com").password("$2a$10$epn0tpIbEXbH.YBWxymkKOUN8p1aU1jFp6/EoTl1D/rNcEDCtNpDK").roles("ADMIN", "USER").build());
        in.createUser(User.withUsername("user@gmail.com").password("$2a$10$o3dr79AU/wHUGJy1znGUhumcagM6JRivGpsRqiNDvy0Xc4ukF6vu6").roles("USER").build());
        return in;

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService()); // for using preloaded data UserDetailsService data
//        provider.setUserDetailsService(userDetailsService); for taking data from dB to UserDetailsService

        return provider;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoderBCrypt(){
        return new BCryptPasswordEncoder();
    }
}
