package com.example.ProiectDistributedSystems.repository;

import com.example.ProiectDistributedSystems.model.Device;
import com.example.ProiectDistributedSystems.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
    Device findFirstByDescriptionAndAddress(String description, String address);
    Device findFirstById(Long id);
    Set<Device> findAllByUser(User user);

//    Iterable<Device> findAllById()
}
