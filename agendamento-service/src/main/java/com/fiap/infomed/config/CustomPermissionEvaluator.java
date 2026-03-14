package com.fiap.infomed.config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(targetDomainObject instanceof Long)) {
            return false;
        }

        Long patientId = (Long) targetDomainObject;
        return hasPermission(authentication, patientId, permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null) || !(targetId instanceof Long)) {
            return false;
        }

        if (!targetType.equals("Patient")) {
            return false;
        }

        Long patientId = (Long) targetId;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_MEDICO") || authority.getAuthority().equals("ROLE_ENFERMEIRO")) {
                return true;
            }
        }

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
            return currentUser.getPatientId() != null && currentUser.getPatientId().equals(patientId);
        }

        return false;
    }
}
