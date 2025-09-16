package com.TCC.model;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Respiracao {
    
@Id
private String id;
private int cicloTotal;
private int cicloAtual;
private String nome;
  private int duracaoInspira;    
      private int duracaoSegura;     
    private int duracaoExpira; 

   @Enumerated(EnumType.STRING)
private Status status;
private LocalDateTime inicio;
private LocalDateTime fim;


    public enum Status {
        PRONTO,
        EM_ANDAMENTO,
        PAUSADO,
        FINALIZADO
    }
}
