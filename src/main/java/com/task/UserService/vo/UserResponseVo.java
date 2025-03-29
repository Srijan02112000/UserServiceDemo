package com.task.UserService.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseVo {

    private String name;
    private String email;
    private String gender;
    private String role;
    private String ipAddress;
    private String country;
}
