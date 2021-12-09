package com.example.ProiectDistributedSystems.service;

import com.example.ProiectDistributedSystems.DTO.SensorReadingMQDTO;
import com.example.ProiectDistributedSystems.configs.MQConfig;

import com.example.ProiectDistributedSystems.model.Log;
import com.example.ProiectDistributedSystems.model.Sensor;
import com.example.ProiectDistributedSystems.repository.LogRepository;
import com.example.ProiectDistributedSystems.repository.SensorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class RabbitMQRecieve {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private LogRepository logRepository;

    private final static String QUEUE_NAME = "hello";

    public boolean isFirstReading = true;
    double previousConsumption = 0f;

    public RabbitMQRecieve(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(SensorReadingMQDTO energy) {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();


        System.out.println(localDate + " " + localTime + " | " + energy.toString());

        Long sensorID = energy.getSensorID();

        Sensor sensor = sensorRepository.findFirstById(sensorID);

        if (sensor != null) {
            System.out.println("FOUND SENSOR : " + sensor.toString());

            Log log = new Log(energy.getTimeStamp().toString(), energy.getValue(), sensor);
            System.out.println("SENSOR LOG : " + log.toString());
            logRepository.save(log);

            if (isFirstReading) {
                double peak = energy.getValue() - previousConsumption;
                energy.setMessage("FIRST");
                simpMessagingTemplate.convertAndSend("/topic/updateService", energy);
                previousConsumption = energy.getValue();
                isFirstReading = false;

            } else {
                double maxSensorValue = sensor.getMaximumValueMonitored();
                System.out.println(" ------------ sensor MAX VAL : " + maxSensorValue);
                if (maxSensorValue < energy.getValue() - previousConsumption) {
                    energy.setMessage("EXCEEDS");
                    System.out.println("consumed energy INFO : " + (energy.getValue() - previousConsumption) + "Kw/h");
                    simpMessagingTemplate.convertAndSend("/topic/updateService", energy);
                    previousConsumption = energy.getValue();

                }else{
                    energy.setMessage("NORMAL");
                    simpMessagingTemplate.convertAndSend("/topic/updateService", energy);
                    previousConsumption = energy.getValue();
                }
            }
        }
    }


}
