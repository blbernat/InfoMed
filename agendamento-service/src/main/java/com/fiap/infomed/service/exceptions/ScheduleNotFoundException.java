package com.fiap.infomed.service.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ScheduleNotFoundException extends EntityNotFoundException {
    public ScheduleNotFoundException(String e) {
        super(e);
    }
}
