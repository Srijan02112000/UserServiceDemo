package com.task.UserService.controller;

import com.task.UserService.model.User;
import com.task.UserService.service.UserManagementService;
import com.task.UserService.vo.UserRequestVo;
import com.task.UserService.vo.UserResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserManagementService userManagementService;

    public UserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/csrf")
    @Operation(
            tags = {"User Service"},
            hidden = true
    )
    public CsrfToken getCsrf(HttpServletRequest request) {
        return request.getAttribute("_csrf") != null ? (CsrfToken) request.getAttribute("_csrf") : null;
    }
    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with name, email, gender, and password. " +
                    "Ensures the email is unique and stores the user's IP address and country.",
            tags = {"User Service"}
    )
    public ResponseEntity<UserResponseVo> register(@RequestBody UserRequestVo user) {
        UserResponseVo newUser = userManagementService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/validate")
    @Operation(
            summary = "Validate user credentials",
            description = "Validates the user's email and password." +
                    " Returns a success response if authentication is successful.",
            tags = {"User Service"}
    )
    public ResponseEntity<?> validate(@RequestBody UserRequestVo user) {
        boolean isValid = userManagementService.validateUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
//

    @GetMapping("/all")
    @Operation(
            summary = "Retrieve all registered users",
            description = "Fetches the list of all registered users. Only accessible to admin users.",
            tags = {"User Service"}
    )
    public ResponseEntity<List<UserResponseVo>> getAllUsers() {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @PostMapping("/delete")
    @Operation(
            summary = "Delete an existing user",
            description = "Deletes a user based on the provided email. Only accessible to admin users.",
            tags = {"User Service"}
    )
    public ResponseEntity<?> deleteUser(@RequestBody UserRequestVo user) {
        userManagementService.deleteUser(user.getEmail());
        return ResponseEntity.ok("User deleted successfully");
    }
}
