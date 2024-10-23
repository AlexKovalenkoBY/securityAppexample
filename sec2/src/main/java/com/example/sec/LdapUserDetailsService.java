package com.example.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.Collection;
import java.util.Collections;

@Service
public class LdapUserDetailsService implements UserDetailsService {

    // private final LdapUserDetailsMapper ldapUserDetailsMapper;

    // @Autowired
    // public LdapUserDetailsService(LdapUserDetailsMapper ldapUserDetailsMapper) {
    //     this.ldapUserDetailsMapper = ldapUserDetailsMapper;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement the logic to load user details from LDAP
        // For simplicity, we will return a dummy UserDetails object
        return new org.springframework.security.core.userdetails.User(
                username,
                "",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}