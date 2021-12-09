package com.example.ProiectDistributedSystems.DTO.sensorCRUD;

import com.example.ProiectDistributedSystems.model.Log;

import java.util.Set;

public class SensorGETresp {

    public static class SensorGETDTO {
        public Long id;
        public String description;
        public Set<Log> logs;
        public Long deviceId;
        public Double maximumValueMonitored;
        public String deviceDecription;

    }

    public Iterable<SensorGETDTO> sensors;
}
