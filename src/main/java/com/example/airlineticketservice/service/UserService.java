package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
}
