package com.apostolis.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean //Allows IoC container to handle, instantiation, configuration and dependedncy injection
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(Authorize -> Authorize
                .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN") //Endpoint available for *user* RestaurantOwner or Admin
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            .csrf(csrf->csrf.disable())
            .cors(cors->cors.configurationSource(corsConfigurationSource()));
        //return null; //Because of this it, throws you at log in page, instead of home page!
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
                CorsConfiguration cfg = new CorsConfiguration();

                cfg.setAllowedOrigins(Arrays.asList(
                    "https://quick-food.app/",
                    "http://localhost:3000"
                )); //Allows only requests from these URLs
                cfg.setAllowedMethods(Collections.singletonList("*")); //All HTTP methods are allowed
                cfg.setAllowCredentials(true); //Allows credentials (cookies, auth headers, TLS client certs)
                cfg.setAllowedHeaders(Collections.singletonList("*")); // Allows all headers
                cfg.setExposedHeaders(Arrays.asList("Authorization")); //Which headers can be exposed to client (Authorization header)
                cfg.setMaxAge(3600L); //How long the cors config is cached in the client (1 hour)

            return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){ //Encrypt password every time a user is created
        return new BCryptPasswordEncoder();
    }
}
