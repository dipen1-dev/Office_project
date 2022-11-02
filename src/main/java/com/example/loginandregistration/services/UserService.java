package com.example.loginandregistration.services;

import com.example.loginandregistration.dtos.UserRegistrationDTO;
import com.example.loginandregistration.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean save(UserRegistrationDTO userRegistrationDTO);
}
