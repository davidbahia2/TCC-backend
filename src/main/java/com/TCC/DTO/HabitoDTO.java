package com.TCC.DTO;

import com.TCC.model.Habito;
import java.time.LocalTime;

public record HabitoDTO(
    Integer id,
    String tituloHabito,
    LocalTime horario,
    String diaSemana,
    Boolean concluida,
    String nomeUsuario
) {
    public static HabitoDTO fromEntity(Habito habito) {
        return new HabitoDTO(
            habito.getId(),
            habito.getTituloHabito(),
            habito.getHorario(),
            habito.getDiaSemana(),
            habito.getConcluida(),
            habito.getUsuario() != null ? habito.getUsuario().getNome() : null
        );
    }
}
