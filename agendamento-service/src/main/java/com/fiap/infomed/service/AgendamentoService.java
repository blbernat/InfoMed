package com.fiap.infomed.service;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.messaging.AgendamentoProducer;
import com.fiap.infomed.repository.AgendamentoRepository;
import com.fiap.infomed.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoProducer producer;
    private final UsuarioRepository usuarioRepository;

    public AgendamentoService(AgendamentoRepository repository, AgendamentoProducer producer, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.producer = producer;
        this.usuarioRepository = usuarioRepository;
    }

    public AgendamentoResponseDTO createAppointment(AgendamentoCreateDTO agendamentoDTO) {
        ConsultaEntity appointment = new ConsultaEntity();
        appointment.setStatus(agendamentoDTO.status());
        appointment.setObservacao(agendamentoDTO.observacao());
        appointment.setDataConsulta(agendamentoDTO.dataConsulta());

        UsuarioEntity paciente = usuarioRepository.findById(agendamentoDTO.pacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com o ID: " + agendamentoDTO.pacienteId()));
        appointment.setPaciente(paciente);

        UsuarioEntity medico = usuarioRepository.findById(agendamentoDTO.medicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com o ID: " + agendamentoDTO.medicoId()));
        appointment.setMedico(medico);

        ConsultaEntity saved = repository.save(appointment);
        producer.sendAgendamentoCreated(saved);
        return mapToAgendamentoResponseDTO(saved);
    }

    public AgendamentoResponseDTO findById(Long id) {
        ConsultaEntity consultaEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com o ID: " + id));
        return mapToAgendamentoResponseDTO(consultaEntity);
    }

    public AgendamentoResponseDTO updateAppointment(AgendamentoUpdateDTO agendamentoDTO) {
        ConsultaEntity existingAppointment = repository.findById(agendamentoDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado para atualização com o ID: " + agendamentoDTO.id()));

        existingAppointment.setStatus(agendamentoDTO.status());
        existingAppointment.setObservacao(agendamentoDTO.observacao());
        existingAppointment.setDataConsulta(agendamentoDTO.dataConsulta());

        UsuarioEntity paciente = usuarioRepository.findById(agendamentoDTO.pacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com o ID: " + agendamentoDTO.pacienteId()));
        existingAppointment.setPaciente(paciente);

        UsuarioEntity medico = usuarioRepository.findById(agendamentoDTO.medicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com o ID: " + agendamentoDTO.medicoId()));
        existingAppointment.setMedico(medico);

        ConsultaEntity updated = repository.save(existingAppointment);
        producer.sendAgendamentoUpdated(updated);
        return mapToAgendamentoResponseDTO(updated);
    }

    public void deleteAppointment(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado para exclusão com o ID: " + id);
        }
        repository.deleteById(id);
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
