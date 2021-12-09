package com.example.ProiectDistributedSystems.controller;

import com.example.ProiectDistributedSystems.DTO.deviceCRUD.*;
import com.example.ProiectDistributedSystems.DTO.deviceCRUD.DeviceGETresp.DeviceGETDTO;
import com.example.ProiectDistributedSystems.DTO.sensorCRUD.*;
import com.example.ProiectDistributedSystems.DTO.sensorCRUD.SensorGETresp.SensorGETDTO;
import com.example.ProiectDistributedSystems.DTO.userCRUD.NormalUserPUTresp;
import com.example.ProiectDistributedSystems.DTO.userCRUD.NormlaUserPUTreq;
import com.example.ProiectDistributedSystems.model.Device;
import com.example.ProiectDistributedSystems.model.Sensor;
import com.example.ProiectDistributedSystems.model.User;
import com.example.ProiectDistributedSystems.repository.DeviceRepository;
import com.example.ProiectDistributedSystems.repository.SensorRepository;
import com.example.ProiectDistributedSystems.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;

    @GetMapping("admin/device-list")
    public ResponseEntity getAllDevices() {
        Iterable<Device> allDevices = deviceRepository.findAll();

        System.out.println("");
        System.out.println("ALL DEVICES" + allDevices);

        DeviceGETresp deviceGETresp = new DeviceGETresp();
        ArrayList<DeviceGETDTO> allDevicesResp = new ArrayList<>();

        for (Device dev : allDevices) {
            DeviceGETDTO newDev = new DeviceGETDTO();
            newDev.id = dev.getId();
            newDev.description = dev.getDescription();
            newDev.maxConsumption = dev.getMaxConsumption();
            newDev.avgConsumption = dev.getAvgConsumption();
            newDev.address = dev.getAddress();
            newDev.sensorId = dev.getSensor() == null ? -1 : dev.getSensor().getId();
            newDev.userId = dev.getUser() == null ? -1 : dev.getUser().getId();
            newDev.username = dev.getUser() == null ? "" : dev.getUser().getUsername();


            allDevicesResp.add(newDev);
        }
        deviceGETresp.devices = allDevicesResp;
        return ResponseEntity.status(HttpStatus.OK).body(deviceGETresp);
    }

    @PostMapping("admin/device")
    public DevicePOSTresp CreateDevice(@RequestBody DevicePOSTreq request) {


        System.out.println();
        System.out.println("ADD DEVICE : " + request.toString());
        Device device = Device.builder()
                .description(request.description)
                .address(request.address)
                .maxConsumption(Double.parseDouble(request.maxConsumption))
                .avgConsumption(Double.parseDouble(request.avgConsumption))
                .build();
        deviceRepository.save(device);
        device = deviceRepository.findFirstByDescriptionAndAddress(request.description, request.address);
        return new DevicePOSTresp(device);
    }


    @PutMapping("admin/device/{guid}")
    public ResponseEntity UpdateDevice(@RequestBody DevicePUTreq request, @PathVariable long guid) {
        NormalUserPUTresp response = new NormalUserPUTresp();

        System.out.println();
        System.out.println("GOT A PUT ON /usr" + request.toString());
        try {
            Device device = deviceRepository.findFirstById(guid);
            Sensor sensor = sensorRepository.findFirstById(request.sensorId);
            User user = userRepository.findFirstById(request.userId);

            if (sensor.getDevice() == null) {
                Set<Device> userDevices = user.getDevices();
                userDevices.add(device);
                user.setDevices(userDevices);
                device.setUser(user);
                device.setSensor(sensor);
                sensor.setDevice(device);

                sensorRepository.save(sensor);
                deviceRepository.save(device);
                userRepository.save(user);

                Iterable<Device> allDevices = deviceRepository.findAll();

                return ResponseEntity.status(HttpStatus.OK).body(allDevices);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR : SENSOR ALREADY HAS A DEVICE");
            }


        } catch (Exception e) {
            System.out.println("EXCEPTION" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR : " + e);
        }
    }

    @DeleteMapping("admin/device/{guid}")
    public DeviceDELETEresp DeleteDevice(@PathVariable long guid) {
        Device device = deviceRepository.findFirstById(guid);
        deviceRepository.delete(device);
        return new DeviceDELETEresp(device);
    }

    // SENSOR --------------------
    @GetMapping("admin/sensor-list")
    public ResponseEntity getAllSensors() {
        Iterable<Sensor> allSensors = sensorRepository.findAll();
        System.out.println("");
        System.out.println("ALL Sensors" + allSensors);
        SensorGETresp sensorGETresp = new SensorGETresp();
//        sensorGETresp.sensors = allSensors;

        ArrayList<SensorGETDTO> sensorsDTO = new ArrayList<>();

        for (Sensor sensor : allSensors) {
            SensorGETDTO sensorDTO = new SensorGETDTO();
            sensorDTO.id = sensor.getId();
            sensorDTO.description = sensor.getDescription();
            sensorDTO.logs = sensor.getLogs();
            sensorDTO.deviceId = sensor.getDevice() == null ? -1 : sensor.getDevice().getId();
            sensorDTO.deviceDecription = sensor.getDevice() == null ? "" : sensor.getDevice().getDescription();

            sensorDTO.maximumValueMonitored = sensor.getMaximumValueMonitored();
            sensorsDTO.add(sensorDTO);
        }
        sensorGETresp.sensors = sensorsDTO;

        return ResponseEntity.status(HttpStatus.OK).body(sensorGETresp);
    }

    @PostMapping("admin/sensor")
    public SensorPOSTresp CreateSensor(@RequestBody SensorPOSTreq request) {
        System.out.println();
        System.out.println("ADD SENSOR : " + request.toString());
        Sensor sensor = Sensor.builder()
                .description(request.description)
                .maximumValueMonitored(Double.parseDouble(request.maximumValueMonitored))
                .build();
        sensorRepository.save(sensor);
        sensor = sensorRepository.findFirstByDescription(request.description);
        return new SensorPOSTresp(sensor);
    }

    @DeleteMapping("admin/sensor/{guid}")
    public SensorDELETEresp DeleteSensor(@PathVariable long guid) {
        System.out.println("GOT A DETELE ON SENSOR " + guid);
        Sensor sensor = sensorRepository.findFirstById(guid);
        sensorRepository.delete(sensor);
        return new SensorDELETEresp(sensor);
    }


    @PutMapping("admin/sensor/{guid}")
    public ResponseEntity UpdateSensor(@RequestBody SensorPUTreq request, @PathVariable long guid) {
        NormalUserPUTresp response = new NormalUserPUTresp();
        System.out.println();
        System.out.println("GOT A PUT ON /usr" + request.toString());
        try {

            Device device = deviceRepository.findFirstById(request.deviceId);
            Sensor sensor = sensorRepository.findFirstById(request.id);

//            if(device)
            device.setSensor(sensor);
            sensor.setDevice(device);
            deviceRepository.save(device);
            sensorRepository.save(sensor);
            return ResponseEntity.status(HttpStatus.OK).body("cebva");
        } catch (Exception e) {
            System.out.println("EXCEPTION" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR : " + e);
        }
    }


}
