package com.fiap.infomed.controller;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.dto.UsuarioUpdateSenhaDTO;
import com.fiap.infomed.entities.ETipoUsuario;
import com.fiap.infomed.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarUsuarios() {
        List<UsuarioResponseDTO> expected = Collections.singletonList(new UsuarioResponseDTO(1L, "Test", "test@test.com", "test@test.com", "12345678901", LocalDate.of(1990, 1, 1), LocalDateTime.now(), ETipoUsuario.PACIENTE));
        when(usuarioService.buscarUsuarios()).thenReturn(expected);

        ResponseEntity<List<UsuarioResponseDTO>> actual = usuarioController.buscarUsuarios();

        assertEquals(200, actual.getStatusCode().value());
        assertEquals(expected, actual.getBody());
        verify(usuarioService).buscarUsuarios();
    }

    @Test
    void testSaveUsuario() {
        UsuarioCreateDTO createDTO = new UsuarioCreateDTO("Test", "test@test.com", "test@test.com", "password", "12345678901", LocalDate.of(1990, 1, 1), ETipoUsuario.PACIENTE);
        doNothing().when(usuarioService).saveUsuario(createDTO);

        ResponseEntity<Void> actual = usuarioController.saveUsuario(createDTO);

        assertEquals(201, actual.getStatusCode().value());
        verify(usuarioService).saveUsuario(createDTO);
    }

    @Test
    void testUpdateUsuario() {
        UsuarioUpdateDTO updateDTO = new UsuarioUpdateDTO("Test", "test@test.com", "test@test.com", "12345678901", LocalDate.of(1990, 1, 1), ETipoUsuario.PACIENTE);
        doNothing().when(usuarioService).updateUsuario(updateDTO);

        ResponseEntity<Void> actual = usuarioController.updateUsuario(updateDTO);

        assertEquals(200, actual.getStatusCode().value());
        verify(usuarioService).updateUsuario(updateDTO);
    }

    @Test
    void testDeleteUsuario() {
        String login = "test@test.com";
        doNothing().when(usuarioService).deleteUsuario(login);

        ResponseEntity<Void> actual = usuarioController.deleteUsuario(login);

        assertEquals(200, actual.getStatusCode().value());
        verify(usuarioService).deleteUsuario(login);
    }

    @Test
    void testUpdateSenhaUsuario() {
        UsuarioUpdateSenhaDTO updateSenhaDTO = new UsuarioUpdateSenhaDTO("test@test.com", "oldPassword", "newPassword");
        doNothing().when(usuarioService).updateSenhaUsuario(updateSenhaDTO);

        ResponseEntity<Void> actual = usuarioController.updateSenhaUsuario(updateSenhaDTO);

        assertEquals(200, actual.getStatusCode().value());
        verify(usuarioService).updateSenhaUsuario(updateSenhaDTO);
    }
}
