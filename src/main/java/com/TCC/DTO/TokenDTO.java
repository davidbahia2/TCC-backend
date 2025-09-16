package com.TCC.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TokenDTO {

    private String nome;
    private Boolean autenticado;
    private Date criado;
    private Date validade;
    private String acessoToken;
    private String atualizaToken;
    private String senha;


 // ...existing code...
public TokenDTO(String nome, boolean autenticado, Date criado, Date validade, String acessoToken, String atualizaToken) {
    this.nome = nome;
    this.autenticado = autenticado;
    this.criado = criado;
    this.validade = validade;
    this.acessoToken = acessoToken;
    this.atualizaToken = atualizaToken;
}
// ...existing code...
    
}
