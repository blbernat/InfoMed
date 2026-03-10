package com.fiap.infomed.repository;

import com.fiap.infomed.entities.HistoricoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricRepository extends JpaRepository<HistoricoEntity, Long> {
}
