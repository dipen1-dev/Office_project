package com.example.loginandregistration.daos;

import com.example.loginandregistration.models.User;

public interface UserRegistrationDao {
    boolean saveOrUpdate(User user);

    boolean isAlreadyRegisteredEmail(String email);

    User findByEmail(String username);
}
