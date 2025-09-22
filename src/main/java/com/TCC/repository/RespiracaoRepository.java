package com.TCC.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.TCC.enums.TipoRespiracao;
import com.TCC.model.Respiracao;

public interface RespiracaoRepository extends MongoRepository<Respiracao, String> {

    List<Respiracao> procurarPorRespiracao(TipoRespiracao tipoRespiracao);

  
    List<Respiracao> procurarUsuarioPorSessao(String usuarioId, LocalDateTime inicio, LocalDateTime fim);
    List<Respiracao> procuraSessaoCompleta(String usuarioId);

    Long contaSessaoPorUsuario(String usuarioId);

    Optional<Respiracao> buscarSessaoIncompleta(String usuarioId);

    @Query("{'usuarioId': ?0, 'dataIncio': {$gte; ?1, $lte: ?2}}")
    List<Respiracao> procurarSessaoPeriodo(String usuarioId, LocalDateTime inicio, LocalDateTime fim);

    @Query("{'usuarioId': ?0, 'tipoRespiracao': ?1, 'sessaoCompleta':true}")
    List<Respiracao> procurarSessaoCompletaPorTipo(String usuarioId, TipoRespiracao tipo);
}
