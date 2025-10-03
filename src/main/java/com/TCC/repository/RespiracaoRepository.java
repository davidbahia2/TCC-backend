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

    // Busca por tipo de respiração
    List<Respiracao> findByTipoRespiracao(TipoRespiracao tipoRespiracao);

    // Busca sessões de um usuário em um período
    @Query("SELECT r FROM Respiracao r WHERE r.usuarioId = :usuarioId AND r.dataInicio >= :inicio AND r.dataInicio <= :fim")
    List<Respiracao> procurarUsuarioPorSessao(
        @Param("usuarioId") int usuarioId, 
        @Param("inicio") LocalDateTime inicio, 
        @Param("fim") LocalDateTime fim
    );

    // Busca todas as sessões completas de um usuário
    @Query("SELECT r FROM Respiracao r WHERE r.usuarioId = :usuarioId AND r.sessaoCompleta = true")
    List<Respiracao> procuraSessaoCompleta(@Param("usuarioId") int usuarioId);

    // Conta total de sessões por usuário
    Long countByUsuarioId(int usuarioId);

    // Busca sessão incompleta por usuário ID
    @Query("SELECT r FROM Respiracao r WHERE r.usuarioId = :usuarioId AND r.sessaoCompleta = false")
    Optional<Respiracao> buscarSessaoIncompleta(@Param("usuarioId") int usuarioId);

    // Busca sessões em um período específico
    @Query("SELECT r FROM Respiracao r WHERE r.usuarioId = :usuarioId AND r.dataInicio >= :inicio AND r.dataInicio <= :fim")
    List<Respiracao> procurarSessaoPeriodo(
        @Param("usuarioId") int usuarioId, 
        @Param("inicio") LocalDateTime inicio, 
        @Param("fim") LocalDateTime fim
    );

    // Busca sessões completas por tipo de respiração
    @Query("SELECT r FROM Respiracao r WHERE r.usuarioId = :usuarioId AND r.tipoRespiracao = :tipo AND r.sessaoCompleta = true")
    List<Respiracao> procurarSessaoCompletaPorTipo(
        @Param("usuarioId") int usuarioId, 
        @Param("tipo") TipoRespiracao tipo
    );
}