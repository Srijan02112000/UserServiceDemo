package com.task.UserService.service.impl;

import com.task.UserService.model.User;
import com.task.UserService.repository.UserRepository;
import com.task.UserService.util.CustomExceptionForUser;
import com.task.UserService.util.UserNotFoundException;
import com.task.UserService.vo.UserRequestVo;
import com.task.UserService.vo.UserResponseVo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class UserManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserManagementServiceImpl userManagementService;

    private UserRequestVo userRequestVo;
    private User user;

    @BeforeEach
    void setUp() {
        userRequestVo = new UserRequestVo();
        userRequestVo.setEmail("srijanram123@gmail.com");
        userRequestVo.setPassword("srijanram123");
        userRequestVo.setName("Srijan Ram");
        userRequestVo.setGender("Male");
        userRequestVo.setRole("ADMIN");

        user = new User();
        user.setEmail(userRequestVo.getEmail());
        user.setPassword("$2a$10$ODUlQWRXTxLbGo50wVWh7uAOcotu3VbwkQwUwlk68HqwSISd.jrLi");
        user.setName(userRequestVo.getName());
        user.setGender(userRequestVo.getGender());
        user.setRole("ADMIN");
    }

    @Test
    void registerUserTest() { // register user save function test
        when(userRepository.existsByEmail(userRequestVo.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(userRequestVo.getPassword())).thenReturn("$2a$10$ODUlQWRXTxLbGo50wVWh7uAOcotu3VbwkQwUwlk68HqwSISd.jrLi");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(Map.of("ip", "152.58.203.196", "country", "India"));

        UserResponseVo response = userManagementService.registerUser(userRequestVo);

        assertNotNull(response);
        assertEquals(userRequestVo.getEmail(), response.getEmail());
        assertEquals("India", response.getCountry());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUserTest2() { // register user email exists test
        when(userRepository.existsByEmail(userRequestVo.getEmail())).thenReturn(true);

        Exception exception = assertThrows(CustomExceptionForUser.class, () ->
                userManagementService.registerUser(userRequestVo)
        );

        assertEquals("Email already exists! Please use a different email.", exception.getMessage());
    }

    @Test
    void validateUserTest() { // validate user normal function test
        when(userRepository.findByEmail(userRequestVo.getEmail())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(userRequestVo.getPassword(), user.getPassword())).thenReturn(true);

        boolean isValid = userManagementService.validateUser(userRequestVo.getEmail(), userRequestVo.getPassword());

        assertTrue(isValid);
    }

    @Test
    void validateUserTest2() { // validate user wrong password test
        when(userRepository.findByEmail(userRequestVo.getEmail())).thenReturn(user);
        when(bCryptPasswordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        boolean isValid = userManagementService.validateUser(userRequestVo.getEmail(), "wrongPassword");

        assertFalse(isValid);
    }

    @Test
    void deleteUserTest() { // delete user normal function test
        when(userRepository.findByEmail(userRequestVo.getEmail())).thenReturn(user);

        userManagementService.deleteUser(userRequestVo.getEmail());

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserTest2() { // delete user not found test
        when(userRepository.findByEmail(userRequestVo.getEmail())).thenReturn(null);

        Exception exception = assertThrows(UserNotFoundException.class, () ->
                userManagementService.deleteUser(userRequestVo.getEmail())
        );

        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void getAllUserstest() { // get all users test
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserResponseVo> users = userManagementService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(user.getEmail(), users.get(0).getEmail());
    }

}