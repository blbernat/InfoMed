package com.fiap.infomed.graphql;

import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.entities.HistoricoEntity;
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
    private final UsuarioRepository usuarioRepository;

    public HistoricoGraphQLController(AgendamentoRepository agendamentoRepository, HistoricRepository historicRepository, UsuarioRepository usuarioRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.historicRepository = historicRepository;
        this.usuarioRepository = usuarioRepository;
    }
//Depois substituir os entity's por DTO's
//Alterar as querys para buscar os valores específicos
    @QueryMapping
    public List<ConsultaEntity> patientAppointments (@Argument Long patientId) {
        return agendamentoRepository.findByPaciente(patientId)
                .stream()
                .filter(c -> c.getPaciente() != null && (patientId).equals(c.getPaciente().getId()))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<ConsultaEntity> futureAppointments(@Argument Long patientId) {
        LocalDateTime now = LocalDateTime.now();
        return agendamentoRepository.findAll().stream()
                .filter(c -> c.getPaciente() != null && (patientId).equals(c.getPaciente().getId()) && c.getDataConsulta().isAfter(now))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<HistoricoEntity> patientHistory (@Argument Long patientId) {
        return historicRepository.findAll().stream()
                .filter(h -> h.getPaciente() != null && patientId.equals(h.getPaciente().getId()))
                .collect(Collectors.toList());
    }
}
