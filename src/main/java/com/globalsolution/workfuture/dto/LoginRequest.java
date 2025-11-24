package com.globalsolution.workfuture.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "{validation.username.required}")
    private String username;
    
    @NotBlank(message = "{validation.password.required}")
    private String password;
}

