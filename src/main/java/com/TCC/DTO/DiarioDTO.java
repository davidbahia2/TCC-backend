package com.TCC.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiarioDTO {
    private Integer usuarioId;
    private String humor; // valores: 1, 2, 3, 4, 5
    private String descricaoPensamentos;
}