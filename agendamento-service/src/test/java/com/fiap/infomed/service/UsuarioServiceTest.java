package com.fiap.infomed.service;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.dto.UsuarioUpdateSenhaDTO;
import com.fiap.infomed.entities.ETipoUsuario;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.repository.UsuarioRepository;
import com.fiap.infomed.service.exceptions.CreateUserException;
import com.fiap.infomed.service.exceptions.InvalidPasswordException;
import com.fiap.infomed.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioEntity usuarioEntity;
    private UsuarioCreateDTO createDTO;
    private UsuarioUpdateDTO updateDTO;
    private UsuarioUpdateSenhaDTO updateSenhaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNome("Usuario Teste");
        usuarioEntity.setEmail("teste@infomed.com");
        usuarioEntity.setLogin("teste");
        usuarioEntity.setCpf("12345678900");
        usuarioEntity.setDataNascimento(LocalDate.of(1990, 1, 1));
        usuarioEntity.setSenha("encodedPassword");
        usuarioEntity.setTipoUsuario(ETipoUsuario.PACIENTE);

        createDTO = new UsuarioCreateDTO("Novo Usuario", "novo@infomed.com", "novo", "123456","00987654321", LocalDate.of(1995, 5, 5), ETipoUsuario.MEDICO);
        updateDTO = new UsuarioUpdateDTO("Usuario Atualizado", "atualizado@infomed.com", "teste", "11122233344", LocalDate.of(1985, 8, 8), ETipoUsuario.PACIENTE);
        updateSenhaDTO = new UsuarioUpdateSenhaDTO("teste", "currentPassword", "newPassword");
    }

    @Test
    void testSaveUsuario() {
        when(passwordEncoder.encode("newPassword")).thenReturn("123456");
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(new UsuarioEntity());

        usuarioService.saveUsuario(createDTO);

        verify(passwordEncoder, times(1)).encode("123456");
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void testUpdateUsuario() {
        when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.findByEmail("atualizado@infomed.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        usuarioService.updateUsuario(updateDTO);

        verify(usuarioRepository, times(1)).findByLogin("teste");
        verify(usuarioRepository, times(1)).findByEmail("atualizado@infomed.com");
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void testUpdateUsuario_EmailInUse() {
        UsuarioEntity otherUser = new UsuarioEntity();
        otherUser.setLogin("other");
        when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.findByEmail("atualizado@infomed.com")).thenReturn(Optional.of(otherUser));

        assertThrows(CreateUserException.class, () -> usuarioService.updateUsuario(updateDTO));

        verify(usuarioRepository, times(1)).findByLogin("teste");
        verify(usuarioRepository, times(1)).findByEmail("atualizado@infomed.com");
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
    }

    @Test
    void testDeleteUsuario() {
        when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(usuarioRepository).delete(usuarioEntity);

        usuarioService.deleteUsuario("teste");

        verify(usuarioRepository, times(1)).findByLogin("teste");
        verify(usuarioRepository, times(1)).delete(usuarioEntity);
    }

    @Test
    void testUpdateSenhaUsuario() {
        when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuarioEntity));
        when(passwordEncoder.matches("currentPassword", "encodedPassword")).thenReturn(true);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        usuarioService.updateSenhaUsuario(updateSenhaDTO);

        verify(usuarioRepository, times(1)).findByLogin("teste");
        verify(passwordEncoder, times(1)).matches("currentPassword", "encodedPassword");
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void testUpdateSenhaUsuario_InvalidCurrentPassword() {
        when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuarioEntity));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> usuarioService.updateSenhaUsuario(new UsuarioUpdateSenhaDTO("teste", "wrongPassword", "newPassword")));

        verify(usuarioRepository, times(1)).findByLogin("teste");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "encodedPassword");
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
    }

    @Test
    void testBuscarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuarioEntity));

        List<UsuarioResponseDTO> response = usuarioService.buscarUsuarios();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(usuarioEntity.getNome(), response.get(0).nome());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        UsuarioEntity foundUser = usuarioService.findUsuarioById(1L);

        assertNotNull(foundUser);
        assertEquals(usuarioEntity.getId(), foundUser.getId());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testFindUsuarioById_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> usuarioService.findUsuarioById(1L));

        verify(usuarioRepository, times(1)).findById(1L);
    }
}
