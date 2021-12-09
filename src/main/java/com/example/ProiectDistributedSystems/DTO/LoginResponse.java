package com.example.ProiectDistributedSystems.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    public boolean isAdmin;
    public boolean foundUser;

    public Long userId;

}
