package com.example.ProiectDistributedSystems.controller;

import com.example.ProiectDistributedSystems.DTO.LoginResponse;
import com.example.ProiectDistributedSystems.DTO.RegisterRequest;
import com.example.ProiectDistributedSystems.DTO.RegisterResponse;
import com.example.ProiectDistributedSystems.model.Admin;
import com.example.ProiectDistributedSystems.model.User;
import com.example.ProiectDistributedSystems.repository.AdminRepository;
import com.example.ProiectDistributedSystems.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class RegisterController {


    private final UserRepository userRepository;
    private final AdminRepository adminRepository;


    public RegisterController(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("got a register request");
        RegisterResponse registerResponse = new RegisterResponse();

        User user = userRepository.findFirstByUsernameAndPassword(registerRequest.name, registerRequest.password);
        Admin admin = adminRepository.findFirstByUsernameAndPassword(registerRequest.name, registerRequest.password);

        System.out.println("registerRequest : " + registerRequest.toString());
        System.out.println("user : " + user);
        System.out.println("admin : " + admin);

        if (user == null && admin == null) {
            user = new User();
//            user.s(registerRequest.email);
            user.setUsername(registerRequest.name);
            user.setPassword(registerRequest.password);

            System.out.println("user = " + user.toString());
            userRepository.save(user);


            registerResponse.registered = true;
            registerResponse.status = "" + HttpStatus.CREATED;
        } else {
            registerResponse.registered = false;
            registerResponse.status = " 400 : " + HttpStatus.BAD_REQUEST;
        }

        return registerResponse;
    }

}
