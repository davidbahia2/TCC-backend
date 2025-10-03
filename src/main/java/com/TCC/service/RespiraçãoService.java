package com.TCC.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;  
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.TCC.DTO.ConfiguracaoRespiracaoDTO;
import com.TCC.DTO.FinalizarSessaoDTO;
import com.TCC.DTO.IniciarSessaoDTO;
import com.TCC.enums.TipoRespiracao;
import com.TCC.model.Respiracao;
import com.TCC.repository.RespiracaoRepository;
import org.springframework.stereotype.Service;

@Service
public class RespiraçãoService {

    @Autowired
    private final RespiracaoRepository respiracaoRepository;

    public RespiraçãoService(RespiracaoRepository respiracaoRepository) {
        this.respiracaoRepository = respiracaoRepository;
    }

    public List<ConfiguracaoRespiracaoDTO> obterConfig() {
        return Arrays.stream(TipoRespiracao.values())
                .map(ConfiguracaoRespiracaoDTO::new)
                .collect(Collectors.toList());
    }

    public ConfiguracaoRespiracaoDTO obterConf(TipoRespiracao tipo) {
        return new ConfiguracaoRespiracaoDTO(tipo);
    }
public Respiracao inicio(IniciarSessaoDTO inicio) {
    // Converte String para int antes de buscar
    int usuarioId = Integer.parseInt(inicio.getUsuarioId());
    
    // Verifica se existe sessão INCOMPLETA (não completa)
    Optional<Respiracao> sessaoAtiva = respiracaoRepository.buscarSessaoIncompleta(usuarioId);
    
    if (sessaoAtiva.isPresent()) {
        throw new RuntimeException("Usuário já possui uma sessão de respiração ativa");
    }
    
    // Cria nova sessão
    Respiracao sessao = new Respiracao();
    sessao.setUsuarioId(usuarioId);
    sessao.setTipoRespiracao(inicio.getTipoRespiracao());
    sessao.setDataInicio(LocalDateTime.now());
    sessao.setSessaoCompleta(false);
    sessao.setCiclosCompletados(0);
    
    return respiracaoRepository.save(sessao);
}

public Respiracao finalizar(int sessaoId, FinalizarSessaoDTO finaliza) {
    Optional<Respiracao> optSessao = respiracaoRepository.findById(sessaoId);
    
    if (optSessao.isEmpty()) {
        throw new RuntimeException("Sessão não encontrada com ID: " + sessaoId);
    }
    
    Respiracao sessao = optSessao.get();
    
    // Verifica se a sessão já foi finalizada
    if (sessao.getSessaoCompleta() != null && sessao.getSessaoCompleta()) {
        throw new RuntimeException("Sessão já foi finalizada anteriormente");
    }
    
    if (sessao.getDataFim() != null) {
        throw new RuntimeException("Sessão já possui data de fim");
    }
    
    // Finaliza a sessão usando o método da entidade
    sessao.finalizarSessao(finaliza.getCiclosCompletados());
    
    return respiracaoRepository.save(sessao);
}

    public Optional<Respiracao> buscaSessaoAtiva(int usuarioId) {
        return respiracaoRepository.buscarSessaoIncompleta(usuarioId);
    }

    public List<Respiracao> buscarHistorico(int usuarioId) {
        return respiracaoRepository.procurarUsuarioPorSessao(usuarioId, null, null);
    }

    public List<Respiracao> buscarSessaoCompleta(int usuarioId) {
        return respiracaoRepository.procuraSessaoCompleta(usuarioId);
    }

    public Map<String, Object> obterEstastistica(int usuarioId) {
        Map<String, Object> statistic = new HashMap<>();

        List<Respiracao> todasSessoes = respiracaoRepository.procurarUsuarioPorSessao(usuarioId, null, null);
        statistic.put("totalSessoes", todasSessoes.size());

        Long sessoesCompletas = respiracaoRepository.countByUsuarioId(usuarioId);
        statistic.put("sessoesCompletas", sessoesCompletas);

        double taxaConclusao = todasSessoes.isEmpty() ? 0
                : (sessoesCompletas.doubleValue() / todasSessoes.size()) * 100;
        statistic.put("taxaConclusao", Math.round((taxaConclusao * 100.0) / 100.0));

        Map<TipoRespiracao, Long> estasticaPorTipo = new HashMap<>();
        for (TipoRespiracao tipo : TipoRespiracao.values()) {
            List<Respiracao> sessoesTipo = respiracaoRepository.procurarSessaoCompletaPorTipo(usuarioId, tipo);
            estasticaPorTipo.put(tipo, Long.valueOf(sessoesTipo.size()));
        }
        statistic.put("sessoesCompletas", estasticaPorTipo);

        Long tempoTotalSegundo = todasSessoes.stream()
                .filter(s -> s.getDuracaoRealSegundos() != null)
                .mapToLong(Respiracao::getDuracaoRealSegundos)
                .sum();

        statistic.put("tempoTotalMinutos", tempoTotalSegundo / 60);

        Optional<Respiracao> ultimaSessao = todasSessoes.stream().findFirst();
        statistic.put("ultimaSessao", ultimaSessao.map(Respiracao::getDataInicio).orElse(null));
        return statistic;

    }

    public List<Respiracao> buscarSessaoDoDia(int usuarioId) {
        LocalDateTime inicioHoje = LocalDate.now().atStartOfDay();
        LocalDateTime fimHoje = inicioHoje.plusDays(1);

        return respiracaoRepository.procurarUsuarioPorSessao(usuarioId, inicioHoje, fimHoje);
    }

    public void cancelarSessaoAtiva(int usuarioId) {
        Optional<Respiracao> sessaoAtiva = respiracaoRepository.buscarSessaoIncompleta(usuarioId);
        if (sessaoAtiva.isPresent()) {
            Respiracao sessao = sessaoAtiva.get();
            sessao.finalizarSessao(0);
            respiracaoRepository.save(sessao);
        } else {
            throw new RuntimeException("nenhuma sessao ativa encontrada");
        }
    }

}