package com.TCC.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuporteDTO {
    private Integer id;
    private String nome;
    private String especialidade;
    private String cidade;
    private String estado;
    private String telefone;
    private List<String> servicos;
}