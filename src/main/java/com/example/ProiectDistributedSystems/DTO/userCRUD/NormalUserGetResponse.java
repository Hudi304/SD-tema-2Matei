package com.example.ProiectDistributedSystems.DTO.userCRUD;

import com.example.ProiectDistributedSystems.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class NormalUserGetResponse {

    public Iterable<User> normalUsers;
}
