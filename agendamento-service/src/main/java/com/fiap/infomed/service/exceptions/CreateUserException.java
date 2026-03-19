package com.fiap.infomed.service.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class CreateUserException extends DataIntegrityViolationException {
    public CreateUserException(String e) {
        super(e);
    }
}
