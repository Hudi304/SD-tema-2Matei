package com.example.ProiectDistributedSystems.DTO.deviceCRUD;

import javax.persistence.Column;

public class DevicePOSTreq {

    public String description;
    public String address;
    public String maxConsumption;
    public String avgConsumption;


    @Override
    public String toString() {
        return "DevicePOSTreq{" +
                "description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maxConsumption='" + maxConsumption + '\'' +
                ", avgConsumption='" + avgConsumption + '\'' +
                '}';
    }
}
