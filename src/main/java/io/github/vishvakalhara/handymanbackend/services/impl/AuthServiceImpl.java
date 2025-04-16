package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.AuthService;
import io.github.vishvakalhara.handymanbackend.services.NotificationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepo userRepo;

    private final NotificationService notificationService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public void createUser(User user) {

        // Checking whether there is already a user with the same name
        if (userRepo.existsUserByEmail(user.getEmail())) {
            throw new AppException("Already there is a user with the same email: " + user.getEmail());
        }

        // Saving the user in db
        User createdUser = userRepo.save(user);

        // Hashing the password
        user.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));

        notificationService.AddNotification("Action Required!",
                "Update your profile picture and bio.",
                "/me/edit",
                true,
                createdUser
        );
    }

    @Override
    public UserDetails authenticate(String email, String password) {

        // If email or password is incorrect this will throw an exception
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generateToken(UserDetails userDetails) {

        HashMap<String, Object> claims = new HashMap<>();

        //    @Value("${jwt.expirationInMs}")
        long jwtExpiration = 86400000L;
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .and()
                .signWith(generateKey())
                .compact();
    }

    @Override
    public UserDetails validateToken(String token) {

        String userEmail = this.extractUsernameFromToken(token);
        return userDetailsService.loadUserByUsername(userEmail);
    }

    private SecretKey generateKey() {
        byte[] keyBytes = this.jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
