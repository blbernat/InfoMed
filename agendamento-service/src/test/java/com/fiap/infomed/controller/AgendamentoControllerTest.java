package com.fiap.infomed.controller;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.dto.UsuarioDTO;
import com.fiap.infomed.entities.EStatusConsulta;
import com.fiap.infomed.entities.ETipoUsuario;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.service.AgendamentoService;
import com.fiap.infomed.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AgendamentoControllerTest {

    @Mock
    private AgendamentoService agendamentoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AgendamentoController agendamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPatientAppointments() {
        long patientId = 1L;
        List<AgendamentoResponseDTO> expected = Collections.singletonList(new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L));
        when(agendamentoService.patientAppointments(patientId)).thenReturn(expected);

        List<AgendamentoResponseDTO> actual = agendamentoController.patientAppointments(patientId);

        assertEquals(expected, actual);
        verify(agendamentoService).patientAppointments(patientId);
    }

    @Test
    void testFutureAppointments() {
        long patientId = 1L;
        List<AgendamentoResponseDTO> expected = Collections.singletonList(new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L));
        when(agendamentoService.futureAppointments(patientId)).thenReturn(expected);

        List<AgendamentoResponseDTO> actual = agendamentoController.futureAppointments(patientId);

        assertEquals(expected, actual);
        verify(agendamentoService).futureAppointments(patientId);
    }

    @Test
    void testCreateAppointment() {
        AgendamentoCreateDTO createDTO = new AgendamentoCreateDTO(EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L);
        AgendamentoResponseDTO expected = new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L);
        when(agendamentoService.createAgendamento(createDTO)).thenReturn(expected);

        AgendamentoResponseDTO actual = agendamentoController.createAppointment(createDTO);

        assertEquals(expected, actual);
        verify(agendamentoService).createAgendamento(createDTO);
    }

    @Test
    void testUpdateAppointment() {
        AgendamentoUpdateDTO updateDTO = new AgendamentoUpdateDTO(1L, EStatusConsulta.REALIZADA, "Observação", LocalDateTime.now());
        AgendamentoResponseDTO expected = new AgendamentoResponseDTO(1L, EStatusConsulta.REALIZADA, "Observação", LocalDateTime.now(), 1L, 2L);
        when(agendamentoService.updateAgendamento(updateDTO)).thenReturn(expected);

        AgendamentoResponseDTO actual = agendamentoController.updateAppointment(updateDTO);

        assertEquals(expected, actual);
        verify(agendamentoService).updateAgendamento(updateDTO);
    }

    @Test
    void testDeleteAppointment() {
        long id = 1L;
        doNothing().when(agendamentoService).deleteAppointment(id);

        agendamentoController.deleteAppointment(id);

        verify(agendamentoService).deleteAppointment(id);
    }

    @Test
    void testFindByMedicoId() {
        long medicoId = 1L;
        List<AgendamentoResponseDTO> expected = Collections.singletonList(new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 1L));
        when(agendamentoService.findByMedicoId(medicoId)).thenReturn(expected);

        List<AgendamentoResponseDTO> actual = agendamentoController.findByMedicoId(medicoId);

        assertEquals(expected, actual);
        verify(agendamentoService).findByMedicoId(medicoId);
    }

    @Test
    void testFindById() {
        long consultaId = 1L;
        AgendamentoResponseDTO expected = new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L);
        when(agendamentoService.findById(consultaId)).thenReturn(expected);

        AgendamentoResponseDTO actual = agendamentoController.findById(consultaId);

        assertEquals(expected, actual);
        verify(agendamentoService).findById(consultaId);
    }

    @Test
    void testPaciente() {
        AgendamentoResponseDTO agendamento = new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setNome("Test Patient");
        usuario.setEmail("patient@test.com");
        usuario.setCpf("12345678901");
        usuario.setTipoUsuario(ETipoUsuario.PACIENTE);
        when(usuarioService.findUsuarioById(1L)).thenReturn(usuario);

        UsuarioDTO actual = agendamentoController.paciente(agendamento);

        assertEquals(usuario.getId(), actual.getId());
        assertEquals(usuario.getNome(), actual.getNome());
        verify(usuarioService).findUsuarioById(1L);
    }

    @Test
    void testPaciente_nullId() {
        AgendamentoResponseDTO agendamento = new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), null, 2L);

        UsuarioDTO actual = agendamentoController.paciente(agendamento);

        assertNull(actual);
        verify(usuarioService, never()).findUsuarioById(anyLong());
    }

    @Test
    void testMedico() {
        AgendamentoResponseDTO agendamento = new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, 2L);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(2L);
        usuario.setNome("Test Doctor");
        usuario.setEmail("doctor@test.com");
        usuario.setCpf("10987654321");
        usuario.setTipoUsuario(ETipoUsuario.MEDICO);
        when(usuarioService.findUsuarioById(2L)).thenReturn(usuario);

        UsuarioDTO actual = agendamentoController.medico(agendamento);

        assertEquals(usuario.getId(), actual.getId());
        assertEquals(usuario.getNome(), actual.getNome());
        verify(usuarioService).findUsuarioById(2L);
    }

    @Test
    void testMedico_nullId() {
        AgendamentoResponseDTO agendamento = new AgendamentoResponseDTO(1L, EStatusConsulta.AGENDADA, "Observação", LocalDateTime.now(), 1L, null);

        UsuarioDTO actual = agendamentoController.medico(agendamento);

        assertNull(actual);
        verify(usuarioService, never()).findUsuarioById(anyLong());
    }
}
