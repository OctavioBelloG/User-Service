package com.example.configuration; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults; 

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.http.HttpMethod;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
        .csrf(csrf -> csrf.disable()) 
        .authorizeHttpRequests(auth -> auth
            // 🔓 ACTUALIZACIÓN: Permitir la ruta EXACTA que tienes en el Controller
            .requestMatchers("/api/v1/auth/**").permitAll() 
         .requestMatchers("/api/v1/users/**").permitAll()
            .requestMatchers("/actuator/**").permitAll()
            // Permitir también con wildcards por si acaso tienes sub-recursos
            .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()

            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/actuator/**").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }
}