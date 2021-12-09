package com.example.ProiectDistributedSystems.repository;


import com.example.ProiectDistributedSystems.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findFirstByUsernameAndPassword(String username, String password);
    User findFirstByUsername(String username);
    User findFirstById(long id);
}
