
package com.TCC.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TCC.model.Habito;

public interface HabitoRepository extends JpaRepository<Habito, Integer> {

    // Busca hábitos por dia da semana
    List<Habito> findByDiaSemana(DayOfWeek diaSemana);
    
    // Query personalizada para buscar por dia
    @Query("SELECT h FROM Habito h WHERE h.diaSemana = :dia")
    List<Habito> buscarPorDia(@Param("dia") DayOfWeek dia);
    
    // Busca hábitos concluídos ou não
    List<Habito> findByConcluida(Boolean concluida);
    
    // Conta hábitos por dia da semana
    Long countByDiaSemana(DayOfWeek diaSemana);
    
    // Conta hábitos concluídos por dia da semana
    Long countByDiaSemanaAndConcluidaTrue(DayOfWeek diaSemana);
    
    // Busca hábitos por dia e status de conclusão
    List<Habito> findByDiaSemanaAndConcluida(DayOfWeek diaSemana, Boolean concluida);
    
    // Busca hábitos de um usuário específico
    @Query("SELECT h FROM Habito h WHERE h.usuario.id = :usuarioId")
    List<Habito> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}