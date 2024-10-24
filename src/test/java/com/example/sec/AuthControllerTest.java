package com.example.sec;
// import com.example.sec.AuthRequest;
// import com.example.sec.AuthResponse;
// import com.example.sec.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateAuthenticationToken_Success() throws Exception {
        AuthRequest authRequest = new AuthRequest("user", "password");
        UserDetails userDetails = mock(UserDetails.class);
        String jwt = "jwtToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(jwt);

        ResponseEntity<AuthResponse> responseEntity = authController.createAuthenticationToken(authRequest);
        AuthResponse response = responseEntity.getBody();

        assertEquals(jwt, response.getJwt());
    }

    @Test
    void testCreateAuthenticationToken_BadCredentials() {
        AuthRequest authRequest = new AuthRequest("user", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(Exception.class, () -> authController.createAuthenticationToken(authRequest));
    }
}