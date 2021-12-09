package com.example.ProiectDistributedSystems.DTO.deviceCRUD;

import com.example.ProiectDistributedSystems.model.Device;
import com.example.ProiectDistributedSystems.model.Sensor;




public class DeviceGETresp {

    public static class DeviceGETDTO {

        public long id;
        public String description;
        public String address;
        public Double maxConsumption;
        public Double avgConsumption;
        public long sensorId;
        public long userId;
        public String username;

        public DeviceGETDTO() {
        }
    }

    public Iterable<DeviceGETDTO> devices;
}
