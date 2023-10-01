package com.example.airlineticketservice.config;

import com.example.airlineticketservice.security.CustomAuthenticationProvider;
import com.example.airlineticketservice.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
public class SecurityConfig{

    private final CustomAuthenticationProvider authenticationProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomAuthenticationProvider authenticationProvider, CustomUserDetailsService customUserDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public RequestCache requestCache() {
        NegatedRequestMatcher excludedMatchers = new NegatedRequestMatcher(
                new OrRequestMatcher(faviconRequestMatcher(), loginRequestMatcher())
        );

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setRequestMatcher(excludedMatchers);
        return requestCache;
    }

    @Bean
    public RequestMatcher faviconRequestMatcher() {
        return new AntPathRequestMatcher("/favicon.ico");
    }

    @Bean
    public RequestMatcher loginRequestMatcher() {
        return new AntPathRequestMatcher("/login");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("https://code.jquery.com/**").permitAll()
                        .requestMatchers("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/**").permitAll()
                        .requestMatchers("/connections/search").permitAll()
                        .requestMatchers("/connections/getDestinationAirport", "/connections/getFlightDates").permitAll()
                        .requestMatchers("/connections/select").permitAll()
                        .requestMatchers("/css/styles.css").permitAll()
                        .requestMatchers("/connections/login").permitAll()
                        .requestMatchers("/connections/confirm").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin()
                    .loginPage("/connections/login")
                    .defaultSuccessUrl("/connections/search", false)
                    .failureUrl("/connections/login?error=true")
                .and()
                .logout()
                    .logoutSuccessUrl("/connections/search?logout=true")
                    .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                    .key("uniqueAndSecret")
                .and()
                .authenticationProvider(authenticationProvider); //set the custom authentication provider


        return http.build();
    }


}
