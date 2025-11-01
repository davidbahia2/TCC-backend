package com.TCC.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TCC.DTO.ConfiguracaoRespiracaoDTO;
import com.TCC.DTO.FinalizarSessaoDTO;
import com.TCC.DTO.IniciarSessaoDTO;
import com.TCC.enums.TipoRespiracao;
import com.TCC.model.Respiracao;
import com.TCC.model.Usuario;
import com.TCC.repository.RespiracaoRepository;

@Service
public class RespiracaoService {

    private final RespiracaoRepository respiracaoRepository;

    @Autowired
    public RespiracaoService(RespiracaoRepository respiracaoRepository) {
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

    public Respiracao inicio(IniciarSessaoDTO inicio, Usuario usuario) {
        int usuarioId = usuario.getId().intValue();

        Optional<Respiracao> sessaoAtiva = respiracaoRepository.buscarSessaoIncompleta(usuarioId);
        if (sessaoAtiva.isPresent()) {
            throw new RuntimeException("Usuário já possui uma sessão de respiração ativa");
        }

        Respiracao sessao = new Respiracao();
        sessao.setUsuario(usuario);
        sessao.setTipoRespiracao(inicio.getTipoRespiracao());
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setSessaoCompleta(false);
        sessao.setCiclosCompletados(0);

        return respiracaoRepository.save(sessao);
    }

    public Respiracao finalizar(int sessaoId, FinalizarSessaoDTO finaliza) {
        Respiracao sessao = respiracaoRepository.findById(sessaoId)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada com ID: " + sessaoId));

        if (Boolean.TRUE.equals(sessao.getSessaoCompleta())) {
            throw new RuntimeException("Sessão já foi finalizada anteriormente");
        }

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

    public Map<String, Object> obterEstatistica(int usuarioId) {
        Map<String, Object> stats = new HashMap<>();

        List<Respiracao> todasSessoes = respiracaoRepository.procurarUsuarioPorSessao(usuarioId, null, null);
        stats.put("totalSessoes", todasSessoes.size());

        long sessoesCompletas = respiracaoRepository.countByUsuario_Id(usuarioId);
        stats.put("sessoesCompletas", sessoesCompletas);

        double taxaConclusao = todasSessoes.isEmpty() ? 0
                : (sessoesCompletas / (double) todasSessoes.size()) * 100;
        stats.put("taxaConclusao", Math.round(taxaConclusao * 100.0) / 100.0);

        Map<TipoRespiracao, Long> estatisticaPorTipo = new HashMap<>();
        for (TipoRespiracao tipo : TipoRespiracao.values()) {
            estatisticaPorTipo.put(tipo,
                    (long) respiracaoRepository.procurarSessaoCompletaPorTipo(usuarioId, tipo).size());
        }
        stats.put("sessoesPorTipo", estatisticaPorTipo);

        long tempoTotalSegundos = todasSessoes.stream()
                .filter(s -> s.getDuracaoRealSegundos() != null)
                .mapToLong(Respiracao::getDuracaoRealSegundos)
                .sum();

        stats.put("tempoTotalMinutos", tempoTotalSegundos / 60);

        todasSessoes.stream()
                .findFirst()
                .ifPresent(s -> stats.put("ultimaSessao", s.getDataInicio()));

        return stats;
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
            throw new RuntimeException("Nenhuma sessão ativa encontrada");
        }
    }
}
