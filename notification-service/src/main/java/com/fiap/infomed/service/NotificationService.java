package com.fiap.infomed.service;

import com.fiap.infomed.dto.AgendamentoMessageDTO;
import com.fiap.infomed.entities.NotificacaoEntity;
import com.fiap.infomed.enums.EStatusNotificacao;
import com.fiap.infomed.repositories.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificacaoRepository repository;

    public NotificationService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public void saveNotification(AgendamentoMessageDTO agendamento) {
        NotificacaoEntity notificacao = new NotificacaoEntity();
        notificacao.setNomeMedico(agendamento.getMedico().getNome());
        notificacao.setNomePaciente(agendamento.getPaciente().getNome());
        notificacao.setDataConsulta(agendamento.getDataConsulta());
        notificacao.setObservacao(agendamento.getObservacao());
        notificacao.setStatus(agendamento.getStatus());
        notificacao.setStatusNotificacao(EStatusNotificacao.PENDENTE);
        notificacao.setIdConsulta(agendamento.getId());

        repository.save(notificacao);
    }

    public void updateNotification(AgendamentoMessageDTO agendamento) {
        NotificacaoEntity notificacao = repository.findByIdConsulta(agendamento.getId()).orElseThrow();
        notificacao.setDataConsulta(agendamento.getDataConsulta());
        notificacao.setObservacao(agendamento.getObservacao());
        notificacao.setStatus(agendamento.getStatus());

        repository.save(notificacao);
    }

    public void deleteNotification(AgendamentoMessageDTO agendamento) {
        NotificacaoEntity notificacao = repository.findByIdConsulta(agendamento.getId()).orElseThrow();
        repository.deleteById(notificacao.getId());
    }

    public void verificarEEnviarNotificacoes() {
        List<NotificacaoEntity> notificacoesPendentes = repository.findByStatusNotificacao(EStatusNotificacao.PENDENTE);
        for (NotificacaoEntity notificacao : notificacoesPendentes) {
            if (notificacao.getDataConsulta().isAfter(LocalDateTime.now())) {
                System.out.println("Enviando notificação para: " + notificacao.getNomePaciente());
                System.out.println(
                        "Lembrete enviado ao paciente "
                                + notificacao.getNomePaciente()
                                + " para consulta no dia "
                                + notificacao.getDataConsulta()
                );
                notificacao.setStatusNotificacao(EStatusNotificacao.ENVIADO);
                repository.save(notificacao);
            }
        }
    }
}
