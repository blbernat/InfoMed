package com.fiap.infomed.repository;

import com.fiap.infomed.entities.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<ConsultaEntity, Long> {

}
