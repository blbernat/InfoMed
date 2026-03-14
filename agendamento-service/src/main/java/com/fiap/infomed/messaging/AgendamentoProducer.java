package com.fiap.infomed.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.infomed.config.RabbitConfig;
import com.fiap.infomed.dto.AgendamentoMessageDTO;
import com.fiap.infomed.dto.UsuarioDTO;
import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.entities.UsuarioEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public AgendamentoProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void sendAgendamentoCreated(ConsultaEntity agendamento) {
        sendEvent(agendamento, RabbitConfig.ROUTING_KEY_CREATED);
    }

    public void sendAgendamentoUpdated(ConsultaEntity updated) {
        sendEvent(updated, RabbitConfig.ROUTING_KEY_UPDATED);
    }

    private void sendEvent(ConsultaEntity agendamento, String routingKey) {
        AgendamentoMessageDTO messageDTO = convertToAgendamentoMessageDTO(agendamento);
        try {
            String jsonMessage = objectMapper.writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE,
                    routingKey,
                    jsonMessage
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting AgendamentoMessageDTO to JSON", e);
        }
    }

    private AgendamentoMessageDTO convertToAgendamentoMessageDTO(ConsultaEntity entity) {
        UsuarioDTO pacienteDTO = convertToUsuarioDTO(entity.getPaciente());
        UsuarioDTO medicoDTO = convertToUsuarioDTO(entity.getMedico());

        return AgendamentoMessageDTO.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .observacao(entity.getObservacao())
                .dataConsulta(entity.getDataConsulta())
                .paciente(pacienteDTO)
                .medico(medicoDTO)
                .build();
    }

    private UsuarioDTO convertToUsuarioDTO(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        return UsuarioDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .build();
    }
}
