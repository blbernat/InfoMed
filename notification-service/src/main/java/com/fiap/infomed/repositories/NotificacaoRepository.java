package com.fiap.infomed.repositories;

import com.fiap.infomed.entities.NotificacaoEntity;
import com.fiap.infomed.enums.EStatusNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, Long> {
    Optional<NotificacaoEntity> findByIdConsulta(Long idConsulta);
    List<NotificacaoEntity> findByStatusNotificacao(EStatusNotificacao status);
}
