package com.TCC.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suporte")
public class Suporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "especialidade", nullable = false)
    private String especialidade;

    @Column(name = "estado", nullable = false)
    private String estado;
}
