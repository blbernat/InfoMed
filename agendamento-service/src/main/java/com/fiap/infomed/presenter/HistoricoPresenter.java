package com.fiap.infomed.presenter;

import com.fiap.infomed.dto.HistoricalResponseDTO;
import com.fiap.infomed.dto.MedicoDTO;
import com.fiap.infomed.entities.HistoricoEntity;
import com.fiap.infomed.entities.UsuarioEntity;

public class HistoricoPresenter {
    private static MedicoDTO toMedicoDTO(UsuarioEntity usuarioEntity) {
        return new MedicoDTO(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getLogin(),
                usuarioEntity.getEmail(),
                usuarioEntity.getTipoUsuario().toString()
        );
    }

    public static HistoricalResponseDTO toHistoricalResponseDTO (HistoricoEntity entity) {
        return new HistoricalResponseDTO(
                entity.getDiagnostico(),
                entity.getTratamento(),
                entity.getDataAtualizacao(),
                toMedicoDTO(entity.getMedico()));
    }
}
