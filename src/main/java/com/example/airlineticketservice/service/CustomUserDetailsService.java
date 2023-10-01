package com.example.airlineticketservice.service;

import com.example.airlineticketservice.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.airlineticketservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final AuthorityService authorityService;

    public CustomUserDetailsService(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userService.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String password = user.getPassword();

        Collection<? extends GrantedAuthority> authorities = this.authorityService
                .findAllByUsername(user.getUsername())
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        String firstName = user.getFirstName();

        return new CustomUserDetails(username, password, authorities, firstName);
    }
}
