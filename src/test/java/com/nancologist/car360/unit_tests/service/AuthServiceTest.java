package com.nancologist.car360.unit_tests.service;

import com.nancologist.car360.dto.auth.SignupRequest;
import com.nancologist.car360.model.User;
import com.nancologist.car360.repository.UserRepository;
import com.nancologist.car360.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "spring.security.jwtSecret=test_secret_123")
public class AuthServiceTest {

    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        String testSecret = "test_jwt_secret_key_that_is_very_secure_1234";

        authService = new AuthService(
                authenticationManager, userRepository, passwordEncoder, testSecret
        );
    }

    @Test
    @DisplayName("Should throw an exception if the username already exists")
    public void testUsernameAlreadyExists() {
        when(userRepository.existsByUsername("john_89")).thenReturn(true);

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("john_89");
        signupRequest.setEmail("john@doe.com");
        signupRequest.setPassword("123456");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.signup(signupRequest));
        assertEquals("Username already taken", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw an exception if the email already exists")
    public void testEmailAlreadyExists() {
        when(userRepository.existsByEmail("john@doe.com")).thenReturn(true);

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("john_89");
        signupRequest.setEmail("john@doe.com");
        signupRequest.setPassword("123456");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.signup(signupRequest));
        assertEquals("Email already exits", exception.getMessage());
    }

    @Test
    @DisplayName("Should persist a new user on successful sign up")
    public void testSuccessPath() {
        SignupRequest request = new SignupRequest();
        request.setUsername("john_doe");
        request.setEmail("john@example.com");
        request.setPassword("password123");

        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        authService.signup(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("john_doe", savedUser.getUsername());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
    }
}
