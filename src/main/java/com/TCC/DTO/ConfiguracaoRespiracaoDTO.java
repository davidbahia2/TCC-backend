package com.TCC.DTO;

import com.TCC.enums.*;

public class ConfiguracaoRespiracaoDTO {
    private TipoRespiracao tipo;
    private String nome;
    private String descricao;
    private int ciclos;
    private int inspirar;
    private int segurar;
    private int expirar;
    private int duracaoTotalSegundos;
    
    public ConfiguracaoRespiracaoDTO(TipoRespiracao tipo) {
        this.tipo = tipo;
        this.nome = tipo.getNome();
        this.descricao = tipo.getDescricao();
        this.ciclos = tipo.getCiclos();
        this.inspirar = tipo.getInspirar();
        this.segurar = tipo.getSegurar();
        this.expirar = tipo.getExpirar();
        this.duracaoTotalSegundos = tipo.getDuracaoTotalSegundos();
    }
    
    // Getters
    public TipoRespiracao getTipo() { return tipo; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getCiclos() { return ciclos; }
    public int getInspirar() { return inspirar; }
    public int getSegurar() { return segurar; }
    public int getExpirar() { return expirar; }
    public int getDuracaoTotalSegundos() { return duracaoTotalSegundos; }
}


