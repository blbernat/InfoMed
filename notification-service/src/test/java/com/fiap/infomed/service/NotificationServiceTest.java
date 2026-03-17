package com.fiap.infomed.service;

import com.fiap.infomed.dto.AgendamentoMessageDTO;
import com.fiap.infomed.dto.UsuarioDTO;
import com.fiap.infomed.entities.NotificacaoEntity;
import com.fiap.infomed.enums.EStatusConsulta;
import com.fiap.infomed.enums.EStatusNotificacao;
import com.fiap.infomed.repositories.NotificacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificacaoRepository repository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNotification() {
        AgendamentoMessageDTO agendamento = mock(AgendamentoMessageDTO.class);
        UsuarioDTO medico = mock(UsuarioDTO.class);
        UsuarioDTO paciente = mock(UsuarioDTO.class);

        when(agendamento.getId()).thenReturn(1L);
        when(agendamento.getMedico()).thenReturn(medico);
        when(agendamento.getPaciente()).thenReturn(paciente);
        when(medico.getNome()).thenReturn("Dr. House");
        when(paciente.getNome()).thenReturn("Paciente Teste");
        when(agendamento.getDataConsulta()).thenReturn(LocalDateTime.now().plusDays(1));
        when(agendamento.getObservacao()).thenReturn("Consulta de rotina");
        when(agendamento.getStatus()).thenReturn(EStatusConsulta.AGENDADA);

        notificationService.saveNotification(agendamento);

        verify(repository, times(1)).save(any(NotificacaoEntity.class));
    }

    @Test
    void testUpdateNotification() {
        AgendamentoMessageDTO agendamento = mock(AgendamentoMessageDTO.class);
        when(agendamento.getId()).thenReturn(1L);
        when(agendamento.getDataConsulta()).thenReturn(LocalDateTime.now().plusDays(2));
        when(agendamento.getObservacao()).thenReturn("Nova observacao");
        when(agendamento.getStatus()).thenReturn(EStatusConsulta.REALIZADA);

        NotificacaoEntity notificacao = new NotificacaoEntity();
        when(repository.findByIdConsulta(1L)).thenReturn(Optional.of(notificacao));

        notificationService.updateNotification(agendamento);

        verify(repository, times(1)).save(notificacao);
        assert notificacao.getDataConsulta().equals(agendamento.getDataConsulta());
        assert notificacao.getObservacao().equals(agendamento.getObservacao());
        assert notificacao.getStatus().equals(agendamento.getStatus());
    }

    @Test
    void testDeleteNotification() {
        AgendamentoMessageDTO agendamento = mock(AgendamentoMessageDTO.class);
        when(agendamento.getId()).thenReturn(1L);

        NotificacaoEntity notificacao = new NotificacaoEntity();
        notificacao.setId(10L);
        when(repository.findByIdConsulta(1L)).thenReturn(Optional.of(notificacao));

        notificationService.deleteNotification(agendamento);

        verify(repository, times(1)).deleteById(10L);
    }

    @Test
    void testVerificarEEnviarNotificacoes() {
        NotificacaoEntity notificacao = new NotificacaoEntity();
        notificacao.setNomePaciente("Paciente Teste");
        notificacao.setDataConsulta(LocalDateTime.now().plusHours(1));
        notificacao.setStatusNotificacao(EStatusNotificacao.PENDENTE);

        when(repository.findByStatusNotificacao(EStatusNotificacao.PENDENTE)).thenReturn(Collections.singletonList(notificacao));

        notificationService.verificarEEnviarNotificacoes();

        verify(repository, times(1)).save(notificacao);
        assert notificacao.getStatusNotificacao().equals(EStatusNotificacao.ENVIADO);
    }
}
