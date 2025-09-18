package com.TCC.DTO;

import com.TCC.enums.UserRole;

public record RegisterDTO(String nome, String senha, UserRole role) {
    
}
