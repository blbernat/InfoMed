package com.fiap.infomed.service;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.entities.EStatusConsulta;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.messaging.AgendamentoProducer;
import com.fiap.infomed.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private AgendamentoProducer producer;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private UsuarioEntity paciente;
    private UsuarioEntity medico;
    private ConsultaEntity consultaEntity;
    private AgendamentoCreateDTO createDTO;
    private AgendamentoUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        paciente = new UsuarioEntity();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");

        medico = new UsuarioEntity();
        medico.setId(2L);
        medico.setNome("Medico Teste");

        consultaEntity = new ConsultaEntity();
        consultaEntity.setId(1L);
        consultaEntity.setPaciente(paciente);
        consultaEntity.setMedico(medico);
        consultaEntity.setDataConsulta(LocalDateTime.now().plusDays(1));
        consultaEntity.setStatus(EStatusConsulta.AGENDADA);
        consultaEntity.setObservacao("Consulta de rotina");

        createDTO = new AgendamentoCreateDTO(EStatusConsulta.AGENDADA, "Consulta de rotina", LocalDateTime.now().plusDays(1), 1L, 2L);
        updateDTO = new AgendamentoUpdateDTO(1L, EStatusConsulta.REALIZADA, "Consulta realizada com sucesso", LocalDateTime.now().plusDays(1));
    }

    @Test
    void testCreateAgendamento() {
        when(usuarioService.findUsuarioById(1L)).thenReturn(paciente);
        when(usuarioService.findUsuarioById(2L)).thenReturn(medico);
        when(repository.save(any(ConsultaEntity.class))).thenReturn(consultaEntity);
        doNothing().when(producer).sendAgendamentoCreated(any(ConsultaEntity.class));

        AgendamentoResponseDTO response = agendamentoService.createAgendamento(createDTO);

        assertNotNull(response);
        assertEquals(consultaEntity.getId(), response.id());
        assertEquals(paciente.getId(), response.pacienteId());
        assertEquals(medico.getId(), response.medicoId());

        verify(repository, times(1)).save(any(ConsultaEntity.class));
        verify(producer, times(1)).sendAgendamentoCreated(any(ConsultaEntity.class));
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultaEntity));

        AgendamentoResponseDTO response = agendamentoService.findById(1L);

        assertNotNull(response);
        assertEquals(consultaEntity.getId(), response.id());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> agendamentoService.findById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAgendamento() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultaEntity));
        when(repository.save(any(ConsultaEntity.class))).thenReturn(consultaEntity);
        doNothing().when(producer).sendAgendamentoUpdated(any(ConsultaEntity.class));

        AgendamentoResponseDTO response = agendamentoService.updateAgendamento(updateDTO);

        assertNotNull(response);
        assertEquals(updateDTO.id(), response.id());
        assertEquals(updateDTO.status(), response.status());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(ConsultaEntity.class));
        verify(producer, times(1)).sendAgendamentoUpdated(any(ConsultaEntity.class));
    }

    @Test
    void testDeleteAppointment() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultaEntity));
        doNothing().when(repository).deleteById(1L);
        doNothing().when(producer).sendAgendamentoDeleted(any(ConsultaEntity.class));

        agendamentoService.deleteAppointment(1L);

        verify(repository, times(1)).deleteById(1L);
        verify(producer, times(1)).sendAgendamentoDeleted(any(ConsultaEntity.class));
    }

    @Test
    void testFindByPacienteId() {
        when(usuarioService.findUsuarioById(1L)).thenReturn(paciente);
        when(repository.findByPacienteId(1L)).thenReturn(Collections.singletonList(consultaEntity));

        List<AgendamentoResponseDTO> response = agendamentoService.findByPacienteId(1L);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(paciente.getId(), response.get(0).pacienteId());

        verify(repository, times(1)).findByPacienteId(1L);
    }

    @Test
    void testFindByMedicoId() {
        when(usuarioService.findUsuarioById(2L)).thenReturn(medico);
        when(repository.findByMedicoId(2L)).thenReturn(Collections.singletonList(consultaEntity));

        List<AgendamentoResponseDTO> response = agendamentoService.findByMedicoId(2L);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(medico.getId(), response.get(0).medicoId());

        verify(repository, times(1)).findByMedicoId(2L);
    }
}
