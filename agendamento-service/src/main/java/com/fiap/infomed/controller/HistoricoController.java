package com.fiap.infomed.controller;

import com.fiap.infomed.dto.HistoricalResponseDTO;
import com.fiap.infomed.dto.HistoricoUpdateDTO;
import com.fiap.infomed.service.HistoricoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HistoricoController {
    private final HistoricoService historicoService;

    public HistoricoController(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<HistoricalResponseDTO> patientHistory(@Argument Long patientId) {
        return historicoService.patientHistory(patientId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('MEDICO')")
    public HistoricalResponseDTO updatePatientHistory(@Argument HistoricoUpdateDTO historicoUpdateDTO) {
        return historicoService.updatePatientHistory(historicoUpdateDTO);
    }
}
