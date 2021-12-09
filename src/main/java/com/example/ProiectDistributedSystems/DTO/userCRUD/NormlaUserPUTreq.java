package com.example.ProiectDistributedSystems.DTO.userCRUD;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NormlaUserPUTreq {

    public Long id;
    public String name;
    public String password;
    public String dateOfBirth;
    public String address;
    public Iterable<Long> deviceIds;

    @Override
    public String toString() {
        return "NormlaUserPUTreq{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", deviceIDs=" + deviceIds +
                '}';
    }
}
