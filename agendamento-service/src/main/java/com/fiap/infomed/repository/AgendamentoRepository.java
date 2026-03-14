package com.fiap.infomed.repository;

import com.fiap.infomed.entities.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<ConsultaEntity, Long> {
    List<ConsultaEntity> findByPacienteId(Long patientId);

    List<ConsultaEntity> findByMedicoId(Long medicoId);
}
