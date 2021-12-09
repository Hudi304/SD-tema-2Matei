package com.example.ProiectDistributedSystems.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String date;

    @Column
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    @JsonBackReference
    private Sensor sensor;

    @Builder
    public Log (String date, Double value, Sensor sensor){
        this.date = date;
        this.value = value;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", value=" + value +
                ", sensor=" + sensor +
                '}';
    }
}
