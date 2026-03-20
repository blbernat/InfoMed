package com.fiap.infomed.controller;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.dto.UsuarioUpdateSenhaDTO;
import com.fiap.infomed.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Busca de usuários",
            description = "Busca todos os usuários cadastrados. " +
                    "Exemplo: http://localhost:8082/api/usuarios",
            responses = {
                    @ApiResponse(description =  "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            summary = "Criação de um novo usuário",
            description = "Criação de novo usuário, onde são feitas as validações das regras de negócio e salva o novo usuário. " +
                    "Deve-se informar um JSON com as informações do usuário. " +
                    "Importante: O CPF deve ser válido e o e-mail deve ter o formato correto. " +
                    "Exemplo: http://localhost:8082/api/usuarios",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Conflict", responseCode = "409"),
                    @ApiResponse(description = "Bad request", responseCode = "400")})
    @PostMapping
    public ResponseEntity<Void> saveUsuario(@Valid @RequestBody UsuarioCreateDTO usuario) {
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(201).build();
    }

    @Operation(
            summary = "Alteração de informações do usuário",
            description = "Alteração de informações do usuário, onde são feitas as validações das regras dos campos e atualiza as informações do usuário. " +
                    "Deve-se informar um JSON com as informações do usuário. O login do usuário é a única informação que não pode ser alterada. " +
                    "Importante: O CPF deve ser válido e o e-mail deve ter o formato correto. " +
                    "Exemplo: http://localhost:8082/api/usuarios",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PutMapping
    public ResponseEntity<Void> updateUsuario(@Valid @RequestBody UsuarioUpdateDTO usuario) {
        usuarioService.updateUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Exclusão de usuário",
            description = "Exclusão de usuário. Deve-se informar o login do usuário que será excluído. " +
                    "Exemplo: http://localhost:8082/api/usuarios/NOME_LOGIN",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("login") String login) {
        usuarioService.deleteUsuario(login);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Alteração da senha do usuário",
            description = "Alteração da senha do usuário. Deve-se informar um JSON com as informações do usuário, senha atual e a nova senha. " +
                    "A senha não pode ser vazia ou possuir apenas espaços. A data de alteração será atualizada automaticamente com a data atual do sistema. " +
                    "Exemplo: http://localhost:8082/api/usuarios/senha",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PutMapping("/senha")
    public ResponseEntity<Void> updateSenhaUsuario(@Valid @RequestBody UsuarioUpdateSenhaDTO usuario) {
        usuarioService.updateSenhaUsuario(usuario);
        return ResponseEntity.ok().build();
    }
}
