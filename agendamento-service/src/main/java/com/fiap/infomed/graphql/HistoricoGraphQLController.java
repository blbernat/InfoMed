package com.fiap.infomed.graphql;

import com.fiap.infomed.dto.ConsultaResponseDTO;
import com.fiap.infomed.dto.HistoricalResponseDTO;
import com.fiap.infomed.presenter.HistoricoPresenter;
import com.fiap.infomed.repository.AgendamentoRepository;
import com.fiap.infomed.repository.HistoricRepository;
import com.fiap.infomed.repository.UsuarioRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HistoricoGraphQLController {
    private final AgendamentoRepository agendamentoRepository;
    private final HistoricRepository historicRepository;

    public HistoricoGraphQLController(AgendamentoRepository agendamentoRepository, HistoricRepository historicRepository, UsuarioRepository usuarioRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.historicRepository = historicRepository;
    }

    @QueryMapping
    public List<ConsultaResponseDTO> patientAppointments (@Argument Long patientId) {
        return agendamentoRepository.findByPaciente(patientId)
                .stream()
                .map(HistoricoPresenter::toConsultaDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<ConsultaResponseDTO> futureAppointments(@Argument Long patientId) {
        LocalDateTime now = LocalDateTime.now();
        return agendamentoRepository.findByPaciente(patientId)
                .stream()
                .filter(c -> c.getPaciente() != null
                        && (patientId).equals(c.getPaciente().getId())
                        && c.getDataConsulta().isAfter(now))
                .map(HistoricoPresenter::toConsultaDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<HistoricalResponseDTO> patientHistory (@Argument Long patientId) {
        return historicRepository.findByPaciente(patientId)
                .stream()
                .map(HistoricoPresenter::toHistoricalResponseDTO)
                .collect(Collectors.toList());
    }
}
