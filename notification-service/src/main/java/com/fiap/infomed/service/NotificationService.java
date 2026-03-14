package com.fiap.infomed.service;

import com.fiap.infomed.dto.AgendamentoMessageDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendReminder(AgendamentoMessageDTO agendamento) {

        System.out.println(
                "Lembrete enviado ao paciente "
                + agendamento.getPaciente().getNome()
                + " para consulta no dia "
                + agendamento.getDataConsulta()
        );

    }
}
