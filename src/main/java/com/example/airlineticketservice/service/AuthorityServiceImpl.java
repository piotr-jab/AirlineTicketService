package com.example.airlineticketservice.service;

import com.example.airlineticketservice.dao.AuthorityRepository;
import com.example.airlineticketservice.entity.Authority;
import com.example.airlineticketservice.entity.Seat;
import com.example.airlineticketservice.entity.SeatId;
import com.example.airlineticketservice.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService{

    private AuthorityRepository authorityRepository;
    private UserService userService;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, UserService userService) {
        this.authorityRepository = authorityRepository;
        this.userService = userService;
    }

    @Override
    public List<Authority> findAllByUsername(String username) {
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return authorityRepository.findAllByUsername(user);
        } else {
            //TODO: Handle the case when the user is not found
            System.out.println("Returned empty list whenuser was not found in findAllByUsername in authority service impl");
            return Collections.emptyList(); // Return an empty list
        }
    }
}
