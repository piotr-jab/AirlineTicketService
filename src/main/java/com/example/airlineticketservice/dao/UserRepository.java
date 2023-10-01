package com.example.airlineticketservice.dao;

import com.example.airlineticketservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
