package com.example.ProiectDistributedSystems.DTO;

public class RegisterRequest {
    public String name;
    public String email;
    public String password;
    public String password1;

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", password1='" + password1 + '\'' +
                '}';
    }
}
