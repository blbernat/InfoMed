package com.fiap.infomed.service;

import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.messaging.AgendamentoProducer;
import com.fiap.infomed.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoProducer producer;

    public AgendamentoService(AgendamentoRepository repository, AgendamentoProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public ConsultaEntity createAppointment(ConsultaEntity appointment) {
        ConsultaEntity saved = repository.save(appointment);
        producer.sendAgendamentoCreated(saved);
        return saved;
    }

    public ConsultaEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com o ID: " + id));
    }

    public ConsultaEntity updateAppointment(ConsultaEntity agendamento) {
        if (!repository.existsById(agendamento.getId())) {
            throw new EntityNotFoundException("Agendamento não encontrado para atualização com o ID: " + agendamento.getId());
        }
        ConsultaEntity updated = repository.save(agendamento);
        producer.sendAgendamentoUpdated(updated);
        return updated;
    }

    public void deleteAppointment(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado para exclusão com o ID: " + id);
        }
        repository.deleteById(id);
    }
}
