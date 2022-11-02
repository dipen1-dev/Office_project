package com.example.loginandregistration.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class UserRegistrationDTO {

    private BigInteger id;

    private String name;

    private String address;

    private  String email;

    private String password;
}
