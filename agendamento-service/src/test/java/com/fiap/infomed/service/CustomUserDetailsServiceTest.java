package com.fiap.infomed.service;

import com.fiap.infomed.entities.ETipoUsuario;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setLogin("teste");
        usuarioEntity.setSenha("password");
        usuarioEntity.setTipoUsuario(ETipoUsuario.PACIENTE);
    }

    @Test
    void testLoadUserByUsername() {
        when(usuarioRepository.findByLogin("teste")).thenReturn(Optional.of(usuarioEntity));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("teste");

        assertNotNull(userDetails);
        assertEquals("teste", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE")));

        verify(usuarioRepository, times(1)).findByLogin("teste");
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(usuarioRepository.findByLogin("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown");
        });

        verify(usuarioRepository, times(1)).findByLogin("unknown");
    }
}
