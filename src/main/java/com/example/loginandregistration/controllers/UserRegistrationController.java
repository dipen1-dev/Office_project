package com.example.loginandregistration.controllers;


import com.example.loginandregistration.Response;
import com.example.loginandregistration.Strings;
import com.example.loginandregistration.daos.UserRegistrationDao;
import com.example.loginandregistration.dtos.LoginDTO;
import com.example.loginandregistration.dtos.UserRegistrationDTO;
import com.example.loginandregistration.services.UserService;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRegistrationDao userRegistrationDao;


    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(
            @RequestParam String username,
            @RequestParam String password
    ){
        System.out.println(username);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(
            @RequestBody UserRegistrationDTO userRegistrationDTO
            ){
        var success = false;
        String message = "";
        System.out.println(userRegistrationDTO.getEmail());
        if (userRegistrationDao.isAlreadyRegisteredEmail(userRegistrationDTO.getEmail()))
            return ResponseEntity.ok(new Response(null, Strings.ALREADY_REGISTERED_EMAIL, success));
        try{
            userService.save(userRegistrationDTO);
            success = true;
            message = "successfully registered";
        } catch (Exception e){
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }
        return ResponseEntity.ok(new Response(null, message, success ));
    }
}
