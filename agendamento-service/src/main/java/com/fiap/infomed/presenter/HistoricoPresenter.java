package com.fiap.infomed.presenter;

import com.fiap.infomed.dto.ConsultaResponseDTO;
import com.fiap.infomed.dto.HistoricalResponseDTO;
import com.fiap.infomed.dto.MedicoDTO;
import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.entities.HistoricoEntity;
import com.fiap.infomed.entities.UsuarioEntity;

public class HistoricoPresenter {
    public static ConsultaResponseDTO toConsultaDTO(ConsultaEntity entity) {
        return new ConsultaResponseDTO(
                entity.getStatus(),
                entity.getObservacao(),
                entity.getDataConsulta(),
                toMedicoDTO(entity.getMedico()));
    }

    private static MedicoDTO toMedicoDTO(UsuarioEntity usuarioEntity) {
        return new MedicoDTO(usuarioEntity.getNome(), usuarioEntity.getTipoUsuario().toString());
    }

    public static HistoricalResponseDTO toHistoricalResponseDTO (HistoricoEntity entity) {
        return new HistoricalResponseDTO(
                entity.getDiagnostico(),
                entity.getTratamento(),
                entity.getDataAtualizacao(),
                toMedicoDTO(entity.getMedico()));
    }
}
