package com.example.learnSecurity.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // to create constructor using any final field in this class
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    
    @Override
    protected void doFilterInternal(
        @Nonnull HttpServletRequest request, 
        @Nonnull HttpServletResponse response, 
        @Nonnull FilterChain filterChain
        )throws ServletException, IOException {
        
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            if(authHeader == null || !authHeader.startsWith("Bearer")){
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            // todo extract the user email from jwt token
            userEmail = jwtService.extractUsername(jwt);

            // to check if user is not authenticate
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                //to check if token is still valid or not
                // if valid then update the token securityContext and send mssg to dispacher controller 
                if(jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                         userDetails.getAuthorities()
                         );
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
    }


}


