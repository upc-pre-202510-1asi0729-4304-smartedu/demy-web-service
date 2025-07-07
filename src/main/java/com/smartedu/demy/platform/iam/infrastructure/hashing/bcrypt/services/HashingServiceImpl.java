package com.smartedu.demy.platform.iam.infrastructure.hashing.bcrypt.services;

import com.smartedu.demy.platform.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link BCryptHashingService} interface.
 * <p>
 * This service provides password hashing and verification functionality
 * using the BCrypt algorithm.
 * </p>
 */
@Service
public class HashingServiceImpl implements BCryptHashingService {
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@code HashingServiceImpl} with a default {@link BCryptPasswordEncoder}.
     */
    HashingServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Hashes the provided raw password using the BCrypt algorithm.
     *
     * @param rawPassword the plain-text password to hash
     * @return the hashed password as a {@link String}
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifies whether the provided raw password matches the previously hashed password.
     *
     * @param rawPassword     the plain-text password to verify
     * @param encodedPassword the previously hashed password
     * @return {@code true} if the raw password matches the encoded password, {@code false} otherwise
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
