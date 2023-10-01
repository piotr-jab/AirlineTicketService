package com.example.airlineticketservice.dao;

import com.example.airlineticketservice.entity.Authority;
import com.example.airlineticketservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    List<Authority> findAllByUsername(User user);
}
