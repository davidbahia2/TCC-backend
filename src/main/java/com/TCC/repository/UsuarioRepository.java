package com.TCC.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TCC.model.Usuario;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends MongoRepository <Usuario, String> {
    
Optional<Usuario> findByNome(String nome);
@Query("SELECT u FROM u WHERE u.nome =:nome")
User findByUsername(@Param("nome")String nome);

}
