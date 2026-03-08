package com.fiap.infomed.service;

import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.messaging.AgendamentoProducer;
import com.fiap.infomed.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

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
}
