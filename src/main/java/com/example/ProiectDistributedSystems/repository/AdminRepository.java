package com.example.ProiectDistributedSystems.repository;

import com.example.ProiectDistributedSystems.model.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends CrudRepository<Admin,Long> {
    Admin findFirstByUsernameAndPassword(String username, String password);
}
