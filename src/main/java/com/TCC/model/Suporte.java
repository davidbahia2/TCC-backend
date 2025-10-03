package com.TCC.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "suporte")
public class Suporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "localizacao")
    private String localizacao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "estado")
    private String estado;

@ElementCollection
@CollectionTable(name = "suporte_servicos", joinColumns = @JoinColumn(name = "suporte_id"))
@Column(name = "servico_nome")
private List<String> servicos = new ArrayList<>();

    public Suporte(String nome, String especialidade, String cidade, String estado, String telefone,
            List<String> servicos) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.servicos = servicos;
    }

}
