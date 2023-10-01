package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findAllByUsername(String Username);
}
