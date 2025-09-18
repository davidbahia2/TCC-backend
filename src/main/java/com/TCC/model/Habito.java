package com.TCC.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    public String tituloHabito;
    @NotNull(message = "O horário da tarefa é obrigatório.")
    private LocalTime horario;
  
    @Field("dia_semana")
    private DayOfWeek diaSemana;
    
    @Field("data_criacao")
    private LocalDateTime dataCriacao;
    
    private Boolean concluida = false;
    
    @Field("data_conclusao")
    private LocalDateTime dataConclusao;
    
}