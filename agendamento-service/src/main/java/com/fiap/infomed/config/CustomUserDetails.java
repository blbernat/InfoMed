package com.fiap.infomed.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final Long patientId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long patientId) {
        super(username, password, authorities);
        this.patientId = patientId;
    }

    public Long getPatientId() {
        return patientId;
    }
}
