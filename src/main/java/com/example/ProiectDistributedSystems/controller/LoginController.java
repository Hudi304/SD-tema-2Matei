package com.example.ProiectDistributedSystems.controller;


import com.example.ProiectDistributedSystems.DTO.LoginRequest;
import com.example.ProiectDistributedSystems.DTO.LoginResponse;
import com.example.ProiectDistributedSystems.model.Admin;

import com.example.ProiectDistributedSystems.model.User;
import com.example.ProiectDistributedSystems.repository.AdminRepository;
import com.example.ProiectDistributedSystems.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        System.out.println("LOGIN  :   got a Login request" + loginRequest.name + "" + loginRequest.password);
        LoginResponse loginResponse = new LoginResponse(false, false, -1l);

        User usr = userRepository.findFirstByUsernameAndPassword(loginRequest.name, loginRequest.password);
        Admin admin = adminRepository.findFirstByUsernameAndPassword(loginRequest.name, loginRequest.password);

        if (admin != null) {
            loginResponse.foundUser = true;
            loginResponse.isAdmin = true;
            System.out.println("IS ADMIN");
        } else {
            if (usr != null) {
                loginResponse.foundUser = true;
                loginResponse.isAdmin = false;
                loginResponse.userId = usr.getId();
                System.out.println("IS NORMAL USER");
            } else {
                loginResponse.foundUser = false;
                loginResponse.isAdmin = false;
                System.out.println("USER NOT FOUND");
            }
        }

        return loginResponse;
    }

    @GetMapping("/login2")
    public String login2() {
        System.out.println("LOGIN 2");
        return "LOGIN";
    }


}
