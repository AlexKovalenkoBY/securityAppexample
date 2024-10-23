package com.example.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
<<<<<<< HEAD
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

=======
import org.springframework.security.config.Customizer;
>>>>>>> 710b1ce1e007dd0bb2e1f8d08c70d0f2901110d2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
            .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                //.requestMatchers("/authenticate").permitAll() // Permit all requests to /authenticate
=======
              //  .requestMatchers("/authenticate2").permitAll() // Permit all requests to /authenticate
              //  .requestMatchers("/").permitAll() // Permit all requests to /authenticate
>>>>>>> 710b1ce1e007dd0bb2e1f8d08c70d0f2901110d2
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(Customizer.withDefaults())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
            .userDnPatterns("uid={0},ou=people") // Adjust this pattern as per your LDAP structure
            .groupSearchBase("ou=groups") // Adjust this base as per your LDAP structure
            .contextSource()
            .url("ldap://localhost:389/dc=test,dc=bpab,dc=internal") // Adjust the LDAP server URL and base DN
            .and()
            .passwordCompare()
            .passwordEncoder(new LdapShaPasswordEncoder()) // Use the appropriate password encoder
            .passwordAttribute("userPassword"); // Adjust the password attribute as per your LDAP structure
    }

    @Bean
    public LdapUserDetailsMapper ldapUserDetailsMapper() {
        return new LdapUserDetailsMapper();
    }
}