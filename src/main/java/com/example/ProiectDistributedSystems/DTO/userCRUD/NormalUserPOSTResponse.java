package com.example.ProiectDistributedSystems.DTO.userCRUD;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NormalUserPOSTResponse {

    public Long id;

    public String name;
    public String password;
    public String dateOfBirth;
    public String address;


}
