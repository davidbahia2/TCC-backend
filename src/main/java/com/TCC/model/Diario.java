package com.TCC.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Diario")
public class Diario {
    

        @Id
    private String id;
    private String userId;
    private String humor; 
    private String descricaoPensamentos;
    private LocalDateTime dataCriacao;

}
