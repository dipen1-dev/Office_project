package com.example.loginandregistration;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private Object body;

    private String message;

    private boolean success;
}
