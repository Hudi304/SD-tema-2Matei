package com.example.ProiectDistributedSystems.repository;

import com.example.ProiectDistributedSystems.model.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long> {
    Sensor findFirstByDescription(String description);

    Sensor findFirstById(Long id);
}
