package com.fiap.infomed.controller;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.dto.UsuarioUpdateSenhaDTO;
import com.fiap.infomed.service.UsuarioService;
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

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Void> saveUsuario(@Valid @RequestBody UsuarioCreateDTO usuario) {
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUsuario(@Valid @RequestBody UsuarioUpdateDTO usuario) {
        usuarioService.updateUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("login") String login) {
        usuarioService.deleteUsuario(login);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/senha")
    public ResponseEntity<Void> updateSenhaUsuario(@Valid @RequestBody UsuarioUpdateSenhaDTO usuario) {
        usuarioService.updateSenhaUsuario(usuario);
        return ResponseEntity.ok().build();
    }
}
