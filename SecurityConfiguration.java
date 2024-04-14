package com.example.learnSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor // to create constructor using any final field in this class
public class SecurityConfiguration {

// at application startup spring security will look for a bean of type securityfilterchain
    // SecurityFilterChain is responsible for configuring all the http secuirty of our application
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        
        try {
            // implementing whiltelisting
            // that user can crete the account if its not already present
            return http.csrf(AbstractHttpConfigurer::disable)
                    // .cors(cors -> corsConfigurationSource())
                    .authorizeHttpRequests(
                            authorize -> authorize.requestMatchers("api/v1/auth/**")
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        } catch (Exception ex) {
            return null;
        }
    }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration corsConfiguration = new CorsConfiguration();
    //     corsConfiguration.setAllowedOrigins(ORIGINS);
    //     corsConfiguration.setAllowedHeaders(HEADERS);
    //     corsConfiguration.setAllowedMethods(METHODS);
    //     corsConfiguration.setAllowCredentials(true);
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", corsConfiguration);
    //     return source;
    // }
}
