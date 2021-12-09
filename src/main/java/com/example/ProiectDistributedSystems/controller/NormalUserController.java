package com.example.ProiectDistributedSystems.controller;

import com.example.ProiectDistributedSystems.DTO.userCRUD.*;
import com.example.ProiectDistributedSystems.model.Device;
import com.example.ProiectDistributedSystems.model.Log;
import com.example.ProiectDistributedSystems.model.Sensor;
import com.example.ProiectDistributedSystems.model.User;
import com.example.ProiectDistributedSystems.repository.DeviceRepository;
import com.example.ProiectDistributedSystems.repository.SensorRepository;
import com.example.ProiectDistributedSystems.repository.UserRepository;
import com.example.ProiectDistributedSystems.service.SensorReadingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@CrossOrigin("*")
public class NormalUserController {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;

    private final SensorReadingsService sensorReadingsService;


    public NormalUserController(UserRepository userRepository, DeviceRepository deviceRepository, SensorRepository sensorRepository, SensorReadingsService sensorReadingsService) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.sensorReadingsService = sensorReadingsService;
    }

    @GetMapping("/user/logs/{guid}")
    public ResponseEntity getAllUserLogs(@PathVariable long guid) {
        System.out.println("GOT a GET  on /user/logs");
        NormalUserGetResponse response = new NormalUserGetResponse();
        Iterable<User> normalUsers = userRepository.findAll();
        response.normalUsers = normalUsers;
        System.out.println("normalUsers" + normalUsers.toString());

        User user = userRepository.findFirstById(guid);
        Set<Device> userDevices = deviceRepository.findAllByUser(user);

        System.out.println("USER DEVICES : " + userDevices.toString());

        for (Device device:userDevices) {
            Sensor deviceSensor = device.getSensor();
            if(deviceSensor != null){
                Set<Log> logs = deviceSensor.getLogs();
                System.out.println("LOGS : " + logs.toString());
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body(userDevices);
    }


    @GetMapping("/user/user-list")
    public NormalUserGetResponse getAllNromalUsers() {
        System.out.println("GOT a GET  on users");
        NormalUserGetResponse response = new NormalUserGetResponse();
        Iterable<User> normalUsers = userRepository.findAll();
        response.normalUsers = normalUsers;
        System.out.println("normalUsers" + normalUsers.toString());
        return response;
    }

    @PostMapping("/user")
    public ResponseEntity CreateUser(@RequestBody NormalUserPOSTRequest request) {
        System.out.println("GOT a POST  on use");
        System.out.println(request.name);
        System.out.println(request.password);
        System.out.println(request.dateOfBirth);
        System.out.println(request.address);

        try {
            User user = userRepository.findFirstByUsername(request.name);
            NormalUserPOSTResponse response = new NormalUserPOSTResponse();

            if (user == null) {
                user = User.builder()
                        .username(request.name)
                        .birthDate(request.dateOfBirth)
                        .password(request.password)
                        .address(request.address)
                        .build();

                userRepository.save(user);

                user = userRepository.findFirstByUsernameAndPassword(request.name, request.password);
                response.id = user.getId();
                response.name = user.getUsername();
                response.password = user.getPassword();
                response.dateOfBirth = user.getBirthDate();
                response.address = user.getAddress();

                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR : username already taken :fire");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR : " + e);
        }
    }


    @PutMapping("/user/{guid}")
    public ResponseEntity UpdateUser(@RequestBody NormlaUserPUTreq request, @PathVariable long guid) {
        NormalUserPUTresp response = new NormalUserPUTresp();

        System.out.println();
        System.out.println("GOT A PUT ON /usr" + request.toString());


        try {
            User usr = userRepository.findFirstById(request.id);

            System.out.println("    FOUND USER : " + usr.toString());

            Set<Device> devices = new HashSet<Device>();
            request.deviceIds.forEach(deviceId -> {
                Device dev = deviceRepository.findFirstById(deviceId);
                System.out.println("DEVICE : " + dev);

                devices.add(dev);
            });

            System.out.println("devices  : " + devices);
            boolean ok = true;

            for (Device device : devices) {
                if (device.getUser() != null && device.getUser().getId() != usr.getId()) {
                    ok = false;
                }
            }
            if (ok) {
                usr.setDevices(devices);
                userRepository.save(usr);

                for (Device device : devices) {
                    device.setUser(usr);
                    deviceRepository.save(device);
                }
                Iterable<User> normalUsers = userRepository.findAll();

                return ResponseEntity.status(HttpStatus.OK).body(normalUsers);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR :  device already has an user");
            }


        } catch (Exception e) {
            System.out.println("EXCEPTION" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR : " + e);
        }
    }

    @DeleteMapping("/user/{guid}")
    public ResponseEntity DeleteUser(@PathVariable long guid) {
        System.out.println("GOT a DELETE  on /user");
        System.out.println(guid);
        NormalUserDELETEResponse response = new NormalUserDELETEResponse();

        try {
            User user = userRepository.findFirstById(guid);
            if (user != null) {
                response.id = user.getId();
                response.name = user.getUsername();
                response.password = user.getPassword();
                response.dateOfBirth = user.getBirthDate();
                response.address = user.getAddress();
                userRepository.delete(user);

                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR :  could not find user with id:" + guid);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR : " + e);
        }
    }


}
