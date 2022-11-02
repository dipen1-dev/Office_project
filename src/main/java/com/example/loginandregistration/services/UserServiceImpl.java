package com.example.loginandregistration.services;

import com.example.loginandregistration.daos.UserRegistrationDao;
import com.example.loginandregistration.dtos.UserRegistrationDTO;
import com.example.loginandregistration.models.Role;
import com.example.loginandregistration.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
    private final UserRegistrationDao userRegistrationDao;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public boolean save(UserRegistrationDTO userRegistrationDTO) {
       return  userRegistrationDao.saveOrUpdate(
                new User(
                        null, userRegistrationDTO.getName(), userRegistrationDTO.getAddress(),
                        userRegistrationDTO.getEmail(), passwordEncoder.encode(userRegistrationDTO.getPassword()),
                        Collections.singleton(new Role("Role_User"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRegistrationDao.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
