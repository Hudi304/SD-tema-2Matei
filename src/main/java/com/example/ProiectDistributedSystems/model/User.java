package com.example.ProiectDistributedSystems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String birthDate;
    @Column
    private String address;

    @Column
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Device> devices;

    @Builder
    public User(String username, String password,String birthDate, String address,Set<Device> devices, Boolean isAdmin ){
        this.id = null;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.address = address;
        this.isAdmin = isAdmin;
        this.devices = devices;
    }



}
