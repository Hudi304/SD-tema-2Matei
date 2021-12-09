package com.example.ProiectDistributedSystems.DTO.sensorCRUD;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SensorPOSTreq {

    public String description;
    public String maximumValueMonitored;


    @Override
    public String toString() {
        return "SensorPOSTreq{" +
                "description='" + description + '\'' +
                ", maximumValueMonitored='" + maximumValueMonitored + '\'' +
                '}';
    }
}
