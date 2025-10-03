package com.TCC.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.TCC.model.Usuario;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    
UserDetails findByNome(String nome);
}
