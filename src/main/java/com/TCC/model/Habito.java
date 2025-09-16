package com.TCC.model;

import java.time.LocalTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Document(collection = "Habito")
public class Habito {
    private String id;

    @NotBlank(message = "esse campo é obrigatorio")
    public String tituloTarefa;
    @NotNull(message = "O horário da tarefa é obrigatório.")
    private LocalTime horario;
    @NotBlank(message = "O dia da semana é obrigatório.")
    private String diaDaSemana;
}
