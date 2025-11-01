package com.TCC.model;

import java.time.LocalDateTime;

import com.TCC.enums.TipoRespiracao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "respiracao")
public class Respiracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dataInicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "dataFim")
    private LocalDateTime dataFim;

    @Column(name = "ciclosCompletados", nullable = false)
    private Integer ciclosCompletados;

    @Column(name = "sessaoCompleta")
    private Boolean sessaoCompleta = false;

    @Column(name = "duracaoRealSegundos")
    private Long duracaoRealSegundos;

    @Enumerated(EnumType.STRING)
    @Column(name = "TipoRespiracao")
    private TipoRespiracao tipoRespiracao;

    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    public void finalizarSessao(int ciclosCompletados) {
        this.ciclosCompletados = ciclosCompletados;
        this.dataFim = LocalDateTime.now();
        this.sessaoCompleta = true;

        if (dataInicio != null && dataFim != null) {
            this.duracaoRealSegundos = java.time.Duration.between(dataInicio, dataFim).getSeconds();
        }
    }
}
