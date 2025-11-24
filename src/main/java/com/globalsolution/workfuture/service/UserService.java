package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.UserDTO;
import com.globalsolution.workfuture.model.User;
import com.globalsolution.workfuture.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserDTO createUser(UserDTO userDTO, String password) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(userDTO.getFullName());
        user.setCurrentPosition(userDTO.getCurrentPosition());
        user.setCurrentCompany(userDTO.getCurrentCompany());
        user.setYearsOfExperience(userDTO.getYearsOfExperience());
        user.setWorkMode(userDTO.getWorkMode());
        user.setRoles(userDTO.getRoles() != null ? userDTO.getRoles() : Set.of(User.Role.ROLE_USER));
        user.setActive(true);
        
        user = userRepository.save(user);
        return new UserDTO(user);
    }
    
    @Cacheable(value = "users", key = "#id")
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user);
    }
    
    @Cacheable(value = "users")
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(UserDTO::new);
    }
    
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (userDTO.getFullName() != null) user.setFullName(userDTO.getFullName());
        if (userDTO.getCurrentPosition() != null) user.setCurrentPosition(userDTO.getCurrentPosition());
        if (userDTO.getCurrentCompany() != null) user.setCurrentCompany(userDTO.getCurrentCompany());
        if (userDTO.getYearsOfExperience() != null) user.setYearsOfExperience(userDTO.getYearsOfExperience());
        if (userDTO.getWorkMode() != null) user.setWorkMode(userDTO.getWorkMode());
        if (userDTO.getRoles() != null) user.setRoles(userDTO.getRoles());
        if (userDTO.getActive() != null) user.setActive(userDTO.getActive());
        
        user = userRepository.save(user);
        return new UserDTO(user);
    }
    
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}

