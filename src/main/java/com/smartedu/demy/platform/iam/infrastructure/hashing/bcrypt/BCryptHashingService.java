package com.smartedu.demy.platform.iam.infrastructure.hashing.bcrypt;

import com.smartedu.demy.platform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Specialized interface for BCrypt-based password hashing.
 * <p>
 * This interface serves as a marker to combine the behavior of both
 * {@link HashingService} and Spring Security's {@link PasswordEncoder}.
 * It allows dependency injection of a BCrypt-specific hashing implementation.
 * </p>
 *
 * @see com.smartedu.demy.platform.iam.infrastructure.hashing.bcrypt.services.HashingServiceImpl
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
