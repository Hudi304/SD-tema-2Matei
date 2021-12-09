package com.example.ProiectDistributedSystems.DTO;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor

public class SensorReadingMQDTO {


    private Long id;
    private Date timeStamp;
    private double value;
    private Long sensorID;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Long getSensorID() {
        return sensorID;
    }

    public void setSensorID(Long sensorID) {
        this.sensorID = sensorID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SensorReadingMQDTO{" + "\n" +
                "\t timeStamp=" + timeStamp + "\n" +
                "\t value=" + value + "\n" +
                "\t sensorID=" + sensorID + "\n" +
                '}' + "\n";
    }
}
