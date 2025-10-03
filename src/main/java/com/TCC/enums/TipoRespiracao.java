package com.TCC.enums;

public enum TipoRespiracao {
    ANSIEDADE("Alívio da Ansiedade", "Técnica calmante para reduzir estresse e preocupação", 5, 4, 7, 5),
    SONO("Ciclo do Sono", "Respiração para relaxamento e preparação para o sono", 6, 4, 4, 4),
    CALMA("Calma", "Exercício de respiração para tranquilidade", 4, 4, 4, 4),
    ENERGIA("Impulsionar Energia", "Respiração energizante para aumentar vitalidade", 8, 4, 4, 2);

    private final String nome;
    private final String descricao;
    private final int ciclos;
    private final int inspirar; // segundos
    private final int segurar; // segundos
    private final int expirar; // segundos

    TipoRespiracao(String nome, String descricao, int ciclos, int inspirar, int segurar, int expirar) {
        this.nome = nome;
        this.descricao = descricao;
        this.ciclos = ciclos;
        this.inspirar = inspirar;
        this.segurar = segurar;
        this.expirar = expirar;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getCiclos() { return ciclos; }
    public int getInspirar() { return inspirar; }
    public int getSegurar() { return segurar; }
    public int getExpirar() { return expirar; }
    
    public int getDuracaoTotalSegundos() {
        return ciclos * (inspirar + segurar + expirar);
    }
}