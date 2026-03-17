package com.fiap.infomed.sender;

import com.fiap.infomed.entities.NotificacaoEntity;

public interface NotificationSender {
    void send(NotificacaoEntity notificacao);
}
