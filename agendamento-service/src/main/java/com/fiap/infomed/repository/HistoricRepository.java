package com.fiap.infomed.repository;

import com.fiap.infomed.entities.HistoricoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricRepository extends JpaRepository<HistoricoEntity, Long> {
    List<HistoricoEntity> findByPacienteId(Long patientId);
}
