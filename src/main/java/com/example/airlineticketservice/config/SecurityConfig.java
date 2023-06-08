package com.example.airlineticketservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setRequestCache(requestCache());
        return successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.csrf().disable() //should not be used
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/js/**", "/css/**").permitAll()
                        .requestMatchers("https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("https://code.jquery.com/**").permitAll()
                        .requestMatchers("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/**").permitAll()
                        .requestMatchers("/connections/search").permitAll()
                        .requestMatchers("/connections/getDestinationAirport", "/connections/getFlightDates").permitAll()
                        .requestMatchers("/connections/select").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/connections/confirm").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin()
                .successHandler(authenticationSuccessHandler()); // Set the custom success handler for redirection after successful login

        return http.build();
    }


}
