package com.example.ProiectDistributedSystems.service;

import com.example.ProiectDistributedSystems.repository.DeviceRepository;
import com.example.ProiectDistributedSystems.repository.LogRepository;
import com.example.ProiectDistributedSystems.repository.SensorRepository;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

import static com.example.ProiectDistributedSystems.configs.WebSocketEndPoints.NOTIFICATION;


@Service
public class SensorReadingsService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private SimpMessagingTemplate template;


    public void connectToLogQueue() throws Exception {
        final String QUEUE_NAME = "hello";
        final String URI = "amqps://kkxmqqnb:udXWnNT4STe4NESQhsqIarfYG-IwOnv0@roedeer.rmq.cloudamqp.com/kkxmqqnb";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for messages");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println("Received '" + message + "'");

//            SensorLogDto sensorLog = SensorLogDto.fromJson(message);
//            Long id = sensorLog.sensorId;
//            Sensor sensor = sensorRepository.findFirstById(id);
//            //Get last measurement
//            SensorLogDto lastLog = lastLogs.get(id);
//            if (lastLog == null) {
//                try {
//                    addLogToSensor(sensorLog);
//                } catch (ApiExceptionResponse e) {
//                    e.printStackTrace();
//                }
//                lastLogs.put(sensorLog.sensorId,sensorLog);
//            } else {
//                double peak = (sensorLog.value - lastLog.value) / (sensorLog.date.getTime() - lastLog.date.getTime());
//                peak*=40000;
//                System.err.println("peak is " + peak);
//                if (peak > sensor.getMaximumValueMonitored()) {
//                    lastLogs.put(sensorLog.sensorId,sensorLog);
//                    System.err.println("aici trimit notificarea");
//                    this.template.convertAndSend(NOTIFICATION,
//                            message + peak);
////                    String userId = sensor.getDevice().getUser().getId().toString();
////                    Notification not = new Notification(measurementData.getTimestamp(),measurementData.getSensor_id(),sensor.getMax_value(),peak);
////                    WsNotification.sendMsg(userId, not.toJson());
//                } else {
//                    try {
//                        addLogToSensor(sensorLog);
//                    } catch (ApiExceptionResponse e) {
//                        e.printStackTrace();
//                    }
//                    lastLogs.put(sensorLog.sensorId,sensorLog);
//                }
//            }

        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }


}
