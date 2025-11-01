package com.TCC.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TCC.enums.TipoRespiracao;
import com.TCC.model.Respiracao;

public interface RespiracaoRepository extends JpaRepository<Respiracao, Integer> {

    List<Respiracao> findByTipoRespiracao(TipoRespiracao tipoRespiracao);

    @Query("""
        SELECT r FROM Respiracao r
        WHERE r.usuario.id = :usuarioId
          AND r.dataInicio BETWEEN :inicio AND :fim
    """)
    List<Respiracao> procurarUsuarioPorSessao(
        @Param("usuarioId") int usuarioId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("""
        SELECT r FROM Respiracao r
        WHERE r.usuario.id = :usuarioId
          AND r.sessaoCompleta = true
    """)
    List<Respiracao> procuraSessaoCompleta(@Param("usuarioId") int usuarioId);

    Long countByUsuario_Id(int usuarioId);

    @Query("""
        SELECT r FROM Respiracao r
        WHERE r.usuario.id = :usuarioId
          AND r.sessaoCompleta = false
    """)
    Optional<Respiracao> buscarSessaoIncompleta(@Param("usuarioId") int usuarioId);

    @Query("""
        SELECT r FROM Respiracao r
        WHERE r.usuario.id = :usuarioId
          AND r.dataInicio BETWEEN :inicio AND :fim
    """)
    List<Respiracao> procurarSessaoPeriodo(
        @Param("usuarioId") int usuarioId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query("""
        SELECT r FROM Respiracao r
        WHERE r.usuario.id = :usuarioId
          AND r.tipoRespiracao = :tipo
          AND r.sessaoCompleta = true
    """)
    List<Respiracao> procurarSessaoCompletaPorTipo(
        @Param("usuarioId") int usuarioId,
        @Param("tipo") TipoRespiracao tipo
    );
}
