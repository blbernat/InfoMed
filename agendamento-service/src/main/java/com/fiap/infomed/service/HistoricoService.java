package com.fiap.infomed.service;

import com.fiap.infomed.dto.ConsultaResponseDTO;
import com.fiap.infomed.dto.HistoricalResponseDTO;
import com.fiap.infomed.dto.HistoricoUpdateDTO;
import com.fiap.infomed.entities.HistoricoEntity;
import com.fiap.infomed.presenter.HistoricoPresenter;
import com.fiap.infomed.repository.AgendamentoRepository;
import com.fiap.infomed.repository.HistoricRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricoService {

    private final AgendamentoRepository agendamentoRepository;
    private final HistoricRepository historicRepository;

    public HistoricoService(AgendamentoRepository agendamentoRepository, HistoricRepository historicRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.historicRepository = historicRepository;
    }

    public List<HistoricalResponseDTO> patientHistory(Long patientId) {
        return historicRepository.findByPacienteId(patientId)
                .stream()
                .map(HistoricoPresenter::toHistoricalResponseDTO)
                .toList();
    }

    public HistoricalResponseDTO updatePatientHistory(HistoricoUpdateDTO historicoUpdateDTO) {
        Optional<HistoricoEntity> optionalHistorico = historicRepository.findById(historicoUpdateDTO.id());

        if (optionalHistorico.isEmpty()) {
            throw new RuntimeException("Histórico não encontrado com o ID: " + historicoUpdateDTO.id());
        }

        HistoricoEntity historicoEntity = optionalHistorico.get();
        historicoEntity.setDiagnostico(historicoUpdateDTO.diagnostico());
        historicoEntity.setTratamento(historicoUpdateDTO.tratamento());
        historicoEntity.setObservacoes(historicoUpdateDTO.observacoes());
        historicoEntity.setDataAtualizacao(LocalDateTime.now());

        HistoricoEntity updatedHistorico = historicRepository.save(historicoEntity);
        return HistoricoPresenter.toHistoricalResponseDTO(updatedHistorico);
    }
}
