package com.fiap.infomed.service;

import com.fiap.infomed.entities.ConsultaEntity;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendReminder(ConsultaEntity agendamento) {

        System.out.println(
                "Lembrete enviado ao paciente "
                + agendamento.getPaciente().getNome()
                + " para consulta no dia "
                + agendamento.getDataConsulta()
        );

    }
}
