package com.globalsolution.workfuture.dto;

import com.globalsolution.workfuture.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "{validation.username.required}")
    @Size(min = 3, max = 50, message = "{validation.username.size}")
    private String username;
    
    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    private String email;
    
    @NotBlank(message = "{validation.fullname.required}")
    @Size(max = 100, message = "{validation.fullname.size}")
    private String fullName;
    
    @Size(max = 200, message = "{validation.position.size}")
    private String currentPosition;
    
    @Size(max = 200, message = "{validation.company.size}")
    private String currentCompany;
    
    private Integer yearsOfExperience;
    
    private User.WorkMode workMode;
    
    private Set<User.Role> roles;
    
    private Boolean active;
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.currentPosition = user.getCurrentPosition();
        this.currentCompany = user.getCurrentCompany();
        this.yearsOfExperience = user.getYearsOfExperience();
        this.workMode = user.getWorkMode();
        this.roles = user.getRoles();
        this.active = user.getActive();
    }
}

