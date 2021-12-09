package com.example.ProiectDistributedSystems.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private Double maxConsumption;

    @Column
    private Double avgConsumption;

    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "sensor_id", nullable = false)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
//    @JsonManagedReference
    private User user;

    @Builder
    public Device (String description, String address,Double maxConsumption,Double avgConsumption,User user, Sensor sensor){
        this.description = description;
        this.address = address;
        this.maxConsumption = maxConsumption;
        this.avgConsumption = avgConsumption;
        this.user = user;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maxConsumption=" + maxConsumption +
                ", avgConsumption=" + avgConsumption +
                ", sensor=" + sensor +
                ", user=" + user +
                '}' + "\n";
    }
}