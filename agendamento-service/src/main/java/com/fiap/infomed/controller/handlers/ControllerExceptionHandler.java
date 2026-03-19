package com.fiap.infomed.controller.handlers;

import com.fiap.infomed.service.exceptions.CreateUserException;
import com.fiap.infomed.service.exceptions.InvalidPasswordException;
import com.fiap.infomed.service.exceptions.ScheduleNotFoundException;
import com.fiap.infomed.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handlerUserNotFoundException(UserNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Erro ao encontrar usuário!");
        problem.setDetail(e.getMessage());
        problem.setType(URI.create("http://localhost:8082/api/usuarios"));
        return problem;
    }

    @ExceptionHandler(CreateUserException.class)
    public ProblemDetail handlerCreateUserException(CreateUserException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        problem.setTitle("Erro ao salvar usuário!");
        problem.setDetail(e.getMessage());
        problem.setType(URI.create("http://localhost:8082/api/usuarios"));
        return problem;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handlerInvalidPasswordException(InvalidPasswordException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problem.setTitle("Senha inválida!");
        problem.setDetail(e.getMessage());
        problem.setType(URI.create("http://localhost:8082/api/usuarios"));
        return problem;
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ProblemDetail handlerScheduleNotFoundException(ScheduleNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Erro ao buscar agendamento!");
        problem.setDetail(e.getMessage());
        problem.setType(URI.create("http://localhost:8082/graphql"));
        return problem;
    }
}
