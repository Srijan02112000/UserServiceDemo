package com.task.UserService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "role")
    private String role ;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "country")
    private String country;

    public User( Long id,String name, String email, String gender, String role, String ipAddress, String country) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.ipAddress = ipAddress;
        this.country = country;
    }
}