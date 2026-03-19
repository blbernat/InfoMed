package com.fiap.infomed.service;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.messaging.AgendamentoProducer;
import com.fiap.infomed.repository.AgendamentoRepository;
import com.fiap.infomed.service.exceptions.ScheduleNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoProducer producer;
    private final UsuarioService usuarioService;

    public AgendamentoService(AgendamentoRepository repository,
                              AgendamentoProducer producer,
                              UsuarioService usuarioRepository) {
        this.repository = repository;
        this.producer = producer;
        this.usuarioService = usuarioRepository;
    }

    public AgendamentoResponseDTO createAgendamento(AgendamentoCreateDTO agendamentoDTO) {
        ConsultaEntity appointment = new ConsultaEntity();
        appointment.setStatus(agendamentoDTO.status());
        appointment.setObservacao(agendamentoDTO.observacao());
        appointment.setDataConsulta(agendamentoDTO.dataConsulta());

        UsuarioEntity paciente = usuarioService.findUsuarioById(agendamentoDTO.pacienteId());
        appointment.setPaciente(paciente);

        UsuarioEntity medico = usuarioService.findUsuarioById(agendamentoDTO.medicoId());
        appointment.setMedico(medico);

        ConsultaEntity saved = repository.save(appointment);
        producer.sendAgendamentoCreated(saved);
        return mapToAgendamentoResponseDTO(saved);
    }

    public AgendamentoResponseDTO findById(Long id) {
        ConsultaEntity consultaEntity = repository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Agendamento não encontrado com o ID: " + id));
        return mapToAgendamentoResponseDTO(consultaEntity);
    }

    public AgendamentoResponseDTO updateAgendamento(AgendamentoUpdateDTO agendamentoDTO) {
        ConsultaEntity existingAppointment = repository.findById(agendamentoDTO.id())
                .orElseThrow(() -> new ScheduleNotFoundException("Agendamento não encontrado para atualização com o ID: " + agendamentoDTO.id()));

        existingAppointment.setStatus(agendamentoDTO.status());
        if (agendamentoDTO.observacao() != null
                && !agendamentoDTO.observacao().isBlank()) {
            existingAppointment.setObservacao(agendamentoDTO.observacao());
        }
        existingAppointment.setDataConsulta(agendamentoDTO.dataConsulta());

        ConsultaEntity updated = repository.save(existingAppointment);
        producer.sendAgendamentoUpdated(updated);
        return mapToAgendamentoResponseDTO(updated);
    }

    public void deleteAppointment(Long id) {
        ConsultaEntity consultaEntity = repository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Agendamento não encontrado para exclusão com o ID: " + id));
        repository.deleteById(id);
        producer.sendAgendamentoDeleted(consultaEntity);
    }

    public List<AgendamentoResponseDTO> findByPacienteId(Long pacienteId) {
        UsuarioEntity paciente = usuarioService.findUsuarioById(pacienteId);
        List<ConsultaEntity> consultaEntity = repository.findByPacienteId(paciente.getId());
        return consultaEntity
                .stream()
                .map(this::mapToAgendamentoResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> findByMedicoId(Long medicoId) {
        UsuarioEntity medico = usuarioService.findUsuarioById(medicoId);
        List<ConsultaEntity> consultaEntity = repository.findByMedicoId(medico.getId());
        return consultaEntity
                .stream()
                .map(this::mapToAgendamentoResponseDTO)
                .toList();
    }

    @Transactional
    public List<AgendamentoResponseDTO> patientAppointments(Long patientId) {
        return repository.findByPacienteId(patientId)
                .stream()
                .map(this::mapToAgendamentoResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> futureAppointments(Long patientId) {
        LocalDateTime now = LocalDateTime.now();
        return repository.findByPacienteId(patientId)
                .stream()
                .filter(c -> c.getPaciente() != null
                        && (patientId).equals(c.getPaciente().getId())
                        && c.getDataConsulta().isAfter(now))
                .map(this::mapToAgendamentoResponseDTO)
                .toList();
    }

    private AgendamentoResponseDTO mapToAgendamentoResponseDTO(ConsultaEntity consultaEntity) {
        if (consultaEntity == null) {
            return null;
        }
        return new AgendamentoResponseDTO(
                consultaEntity.getId(),
                consultaEntity.getStatus(),
                consultaEntity.getObservacao(),
                consultaEntity.getDataConsulta(),
                consultaEntity.getPaciente() != null ? consultaEntity.getPaciente().getId() : null,
                consultaEntity.getMedico() != null ? consultaEntity.getMedico().getId() : null
        );
    }
}
