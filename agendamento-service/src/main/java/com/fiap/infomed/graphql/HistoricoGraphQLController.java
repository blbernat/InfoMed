package com.fiap.infomed.graphql;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.dto.ConsultaResponseDTO;
import com.fiap.infomed.dto.HistoricalResponseDTO;
import com.fiap.infomed.dto.HistoricoUpdateDTO;
import com.fiap.infomed.entities.HistoricoEntity;
import com.fiap.infomed.presenter.HistoricoPresenter;
import com.fiap.infomed.repository.AgendamentoRepository;
import com.fiap.infomed.repository.HistoricRepository;
import com.fiap.infomed.repository.UsuarioRepository;
import com.fiap.infomed.service.AgendamentoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HistoricoGraphQLController {
    private final AgendamentoRepository agendamentoRepository;
    private final HistoricRepository historicRepository;
    private final AgendamentoService agendamentoService;

    public HistoricoGraphQLController(AgendamentoRepository agendamentoRepository, HistoricRepository historicRepository, UsuarioRepository usuarioRepository, AgendamentoService agendamentoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.historicRepository = historicRepository;
        this.agendamentoService = agendamentoService;
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<ConsultaResponseDTO> patientAppointments (@Argument Long patientId) {
        return agendamentoRepository.findByPacienteId(patientId)
                .stream()
                .map(HistoricoPresenter::toConsultaDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<ConsultaResponseDTO> futureAppointments(@Argument Long patientId) {
        LocalDateTime now = LocalDateTime.now();
        return agendamentoRepository.findByPacienteId(patientId)
                .stream()
                .filter(c -> c.getPaciente() != null
                        && (patientId).equals(c.getPaciente().getId())
                        && c.getDataConsulta().isAfter(now))
                .map(HistoricoPresenter::toConsultaDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<HistoricalResponseDTO> patientHistory (@Argument Long patientId) {
        return historicRepository.findByPacienteId(patientId)
                .stream()
                .map(HistoricoPresenter::toHistoricalResponseDTO)
                .collect(Collectors.toList());
    }

    @MutationMapping
    @PreAuthorize("hasRole('MEDICO')")
    public HistoricalResponseDTO updatePatientHistory(@Argument HistoricoUpdateDTO historicoUpdateDTO) {
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

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public AgendamentoResponseDTO createAppointment(@Argument AgendamentoCreateDTO agendamentoCreateDTO) {
        return agendamentoService.createAppointment(agendamentoCreateDTO);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public AgendamentoResponseDTO updateAppointment(@Argument AgendamentoUpdateDTO agendamentoUpdateDTO) {
        return agendamentoService.updateAppointment(agendamentoUpdateDTO);
    }
}
