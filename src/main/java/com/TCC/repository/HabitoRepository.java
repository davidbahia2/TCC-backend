package com.TCC.repository;

import com.TCC.model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Integer> {

    List<Habito> findByUsuario_Id(Integer usuarioId);

    List<Habito> findByUsuario_IdAndDiaSemana(Integer usuarioId, String diaSemana);

    List<Habito> findByUsuario_IdAndConcluida(Integer usuarioId, Boolean concluido);

    List<Habito> findByUsuario_IdAndConcluidaFalse(Integer usuarioId);

    List<Habito> findByUsuario_IdAndConcluidaTrueAndDataConclusaoBetween(
        Integer usuarioId,
        LocalDateTime inicio,
        LocalDateTime fim
    );

    @Query("""
        SELECT COUNT(h)
        FROM Habito h
        WHERE h.usuario.id = :usuarioId
          AND h.concluida = true
          AND h.dataConclusao >= :inicioSemana
    """)
    Long contarHabitosConcluidosSemana(@Param("usuarioId") Integer usuarioId,
                                       @Param("inicioSemana") LocalDateTime inicioSemana);

    @Query("""
        SELECT 
            (COUNT(CASE WHEN h.concluida = true THEN 1 END) * 100.0 / COUNT(h))
        FROM Habito h
        WHERE h.usuario.id = :usuarioId
          AND h.dataCriacao >= :inicioSemana
    """)
    Double calcularTaxaConclusaoSemana(@Param("usuarioId") Integer usuarioId,
                                       @Param("inicioSemana") LocalDateTime inicioSemana);
}
