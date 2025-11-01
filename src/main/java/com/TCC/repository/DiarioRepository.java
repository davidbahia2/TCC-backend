package com.TCC.repository;

import com.TCC.model.Diario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiarioRepository extends JpaRepository<Diario, Integer> {

    // Buscar todos os diários de um usuário
    List<Diario> findByUsuario_Id(Integer usuarioId);

    // Buscar diários de um usuário ordenados por data (mais recentes primeiro)
    List<Diario> findByUsuario_IdOrderByDataCriacaoDesc(Integer usuarioId);

    // Buscar diários de um usuário em um período específico
    List<Diario> findByUsuario_IdAndDataCriacaoBetween(
            Integer usuarioId,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    // Buscar diários por humor específico
    List<Diario> findByUsuario_IdAndHumor(Integer usuarioId, String humor);

    // Calcular humor médio da semana
    @Query("""
       SELECT AVG(
           CASE 
               WHEN d.humor = '5' THEN 5
               WHEN d.humor = '4' THEN 4
               WHEN d.humor = '3' THEN 3
               WHEN d.humor = '2' THEN 2
               WHEN d.humor = '1' THEN 1
               ELSE 0 
           END
       )
       FROM Diario d
       WHERE d.usuario.id = :usuarioId
         AND d.dataCriacao >= :inicioSemana
       """)
    Double calcularHumorMedioSemana(
            @Param("usuarioId") Integer usuarioId,
            @Param("inicioSemana") LocalDateTime inicioSemana
    );

    // Contar entradas da semana
    @Query("""
        SELECT COUNT(d)
        FROM Diario d
        WHERE d.usuario.id = :usuarioId
          AND d.dataCriacao >= :inicioSemana
        """)
    Long contarEntradasSemana(
            @Param("usuarioId") Integer usuarioId,
            @Param("inicioSemana") LocalDateTime inicioSemana
    );
}
