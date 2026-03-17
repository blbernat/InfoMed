package com.fiap.infomed.messaging;

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

    public AgendamentoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAgendamentoCreated(ConsultaEntity agendamento) {
        sendEvent(agendamento, RabbitConfig.ROUTING_KEY_CREATED);
    }

    public void sendAgendamentoUpdated(ConsultaEntity updated) {
        sendEvent(updated, RabbitConfig.ROUTING_KEY_UPDATED);
    }

    public void sendAgendamentoDeleted(ConsultaEntity deleted) {
        sendEvent(deleted, RabbitConfig.ROUTING_KEY_DELETED);
    }

    private void sendEvent(ConsultaEntity agendamento, String routingKey) {
        AgendamentoMessageDTO messageDTO = convertToAgendamentoMessageDTO(agendamento);
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                routingKey,
                messageDTO
        );
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
