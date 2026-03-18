package com.fiap.infomed.sender;

import com.fiap.infomed.entities.NotificacaoEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificationSender implements NotificationSender {
    @Override
    public void send(NotificacaoEntity notificacao) {
        System.out.println("Enviando notificação para: " + notificacao.getNomePaciente());
        System.out.println(
                "Lembrete enviado ao paciente "
                        + notificacao.getNomePaciente()
                        + " para consulta no dia "
                        + notificacao.getDataConsulta()
        );
    }
}
