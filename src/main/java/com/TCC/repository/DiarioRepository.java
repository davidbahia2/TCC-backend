package com.TCC.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TCC.model.Diario;

public interface DiarioRepository extends JpaRepository<Diario, Integer> {

    // Busca todos os diários de um usuário
    @Query("SELECT d FROM Diario d WHERE d.userId = :userId")
    List<Diario> procuraPorId(@Param("userId") int userId);

    // Busca diários de um usuário em uma data específica
    @Query("SELECT d FROM Diario d WHERE d.userId = :userId AND d.dataCriacao = :data")
    List<Diario> procuraPorIdData(
        @Param("userId") int userId, 
        @Param("data") LocalDateTime data
    );

    // Conta quantos diários um usuário tem em uma data
    @Query("SELECT COUNT(d) FROM Diario d WHERE d.userId = :userId AND d.dataCriacao = :dataCriacao")
    long contaPorIdData(
        @Param("userId") int userId, 
        @Param("dataCriacao") LocalDateTime dataCriacao
    );

    // Conta total de diários de um usuário
    @Query("SELECT COUNT(d) FROM Diario d WHERE d.userId = :userId")
    long contaPorId(@Param("userId") int userId);
}
