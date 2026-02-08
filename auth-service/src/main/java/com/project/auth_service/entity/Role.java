package com.project.auth_service.entity;

import java.util.Optional;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_SERVICE;

    public static Optional<Role> from(String value) {
        if (value == null) return Optional.empty();

        try {
            return Optional.of(Role.valueOf(value.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
