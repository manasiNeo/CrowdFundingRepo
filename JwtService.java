package com.example.learnSecurity.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    
    @Value("${application.security.jwt.secret_key}")
    private static final String SECRET_KEY = "";

    // @Value("${application.security.jwt.expiration}")
    // private long expiration_date;

    // @Value("${application.security.jwt.refresh_token.expiration}")
    // private long refresh_expiration_date;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T>claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //to generate token from userdetails only
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
      }

    //to generat token from extra claims
    // to check if token is still valid or not, also generating token
    public String generateToken(
        Map<String, Object> extractClaims,
        UserDetails userDetails
    ){
        return Jwts
            .builder()
            .setClaims(extractClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000 *60 *96))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    //to validate a token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        // to get if the username in the token is same as provided from the client 
        return (username.equals(userDetails.getUsername()))  && !isTokenExpired(token);
    }

    //to check if token is expired or not
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    //to get expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //to extract all the claims
    private Claims extractAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    //to get the secret key from browser and decode it
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
}
