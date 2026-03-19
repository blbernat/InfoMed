package com.fiap.infomed.service.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String e) {
        super(e);
    }
}
