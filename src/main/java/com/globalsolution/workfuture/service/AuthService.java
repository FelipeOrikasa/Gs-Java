package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.LoginRequest;
import com.globalsolution.workfuture.dto.LoginResponse;
import com.globalsolution.workfuture.dto.UserDTO;
import com.globalsolution.workfuture.model.User;
import com.globalsolution.workfuture.repository.UserRepository;
import com.globalsolution.workfuture.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        User user = userRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setUser(new UserDTO(user));
        
        return response;
    }
}

