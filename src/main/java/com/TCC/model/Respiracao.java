package com.TCC.model;

import java.io.ObjectInputFilter.Status;
import java.time.Duration;
import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.TCC.enums.*;

import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "respiracao")
public class Respiracao {
    
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    

    private TipoRespiracao tipoRespiracao;
    

    @Column(name = "dataInicio")
    private LocalDateTime dataInicio;
    
@Column(name = "dataFim")
    private LocalDateTime dataFim;
    
    private Integer ciclosCompletados = 0;
    
@Column(name = "sessaocompleta")
    private Boolean sessaoCompleta = false;
    
@Column(name = "duracaoRealSegundos")
    private Long duracaoRealSegundos;
      
    private int usuarioId; 
    
  public Respiracao (TipoRespiracao tipoRespiracao, int usuarioId) {
        this.tipoRespiracao = tipoRespiracao;
        this.usuarioId = usuarioId;
        this.dataInicio = LocalDateTime.now();
    }

     public void finalizarSessao(int ciclosCompletados) {
        this.dataFim = LocalDateTime.now();
        this.ciclosCompletados = ciclosCompletados;
        this.sessaoCompleta = (ciclosCompletados >= tipoRespiracao.getCiclos());
        this.duracaoRealSegundos = Duration.between(dataInicio, dataFim).getSeconds();
        this.duracaoRealSegundos = java.time.Duration.between(dataInicio, dataFim).getSeconds();
    
}
}