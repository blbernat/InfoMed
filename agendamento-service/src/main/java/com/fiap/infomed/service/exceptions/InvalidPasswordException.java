package com.fiap.infomed.service.exceptions;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException(String e) {
        super(e);
    }
}
