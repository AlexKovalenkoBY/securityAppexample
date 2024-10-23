package com.example.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
<<<<<<< HEAD
import org.springframework.security.core.context.SecurityContextHolder;
=======
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.Person;
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> 710b1ce1e007dd0bb2e1f8d08c70d0f2901110d2
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
<<<<<<< HEAD
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
=======
    private JwtTokenUtil jwtUtil;

    @PostMapping("/authenticate2")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authenticationRequest) {
>>>>>>> 710b1ce1e007dd0bb2e1f8d08c70d0f2901110d2
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

<<<<<<< HEAD
        String token = jwtUtil.generateToken(authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
=======
        String token = jwtUtil.generateTokenViaUsername(
            authenticationRequest.getUsername()
            );

        return ResponseEntity.ok(new AuthResponse(token));
    }
    @GetMapping("/authenticate2")
    public String authenticateGet(@RequestBody AuthRequest authenticationRequest, @AuthenticationPrincipal Person person) {
		return "Hello, " + person.getSn() + "!!";
        
}
>>>>>>> 710b1ce1e007dd0bb2e1f8d08c70d0f2901110d2
}