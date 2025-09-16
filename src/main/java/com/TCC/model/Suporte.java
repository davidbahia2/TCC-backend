package com.TCC.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "suporte")
public class Suporte {
    
@Id
private String id;
@NotBlank(message = "campo obrigatorio")
private String localizacao;
@NotBlank(message = "campo obrigatorio")
private String nome;
@NotBlank(message = "campo obrigatorio")
private String telefone;



}
