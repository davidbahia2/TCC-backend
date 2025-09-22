package com.TCC.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.DTO.ConfiguracaoRespiracaoDTO;
import com.TCC.DTO.FinalizarSessaoDTO;
import com.TCC.DTO.IniciarSessaoDTO;
import com.TCC.model.Respiracao;
import com.TCC.service.RespiraçãoService;

@RestController
@RequestMapping("/respirar")
public class RespiracaoController {

    @Autowired
    private RespiraçãoService respiracaoService;

    @GetMapping("/configuracoes")
    public ResponseEntity<List<ConfiguracaoRespiracaoDTO>> obterConfiguracoes() {
        List<ConfiguracaoRespiracaoDTO> configuracoes = respiracaoService.obterConfig();
        return ResponseEntity.ok(configuracoes);
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Respiracao> iniciarSessao(@RequestBody IniciarSessaoDTO dto) {
        try {
            Respiracao sessao = respiracaoService.inicio(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{sessaoId}/finalizar")
    public ResponseEntity<Respiracao> finalizarSessao(@PathVariable String sessaoId,
            @RequestBody FinalizarSessaoDTO dto) {
        try {
            Respiracao sessao = respiracaoService.finalizar(sessaoId, dto);
            return ResponseEntity.ok(sessao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
  @GetMapping("/ativa/{usuarioId}")
    public ResponseEntity<Respiracao> buscarSessaoAtiva(@PathVariable String usuarioId) {
        Optional<Respiracao> sessao = respiracaoService.buscaSessaoAtiva(usuarioId);
        return sessao.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

        @DeleteMapping("/cancelar/{usuarioId}")
    public ResponseEntity<Void> cancelarSessaoAtiva(@PathVariable String usuarioId) {
        try {
            respiracaoService.cancelarSessaoAtiva(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
 // Histórico de sessões
    @GetMapping("/historico/{usuarioId}")
    public ResponseEntity<List<Respiracao>> buscarHistorico(@PathVariable String usuarioId) {
        List<Respiracao> historico = respiracaoService.buscarHistorico(usuarioId);
        return ResponseEntity.ok(historico);
    }
    
    // Sessões completas
    @GetMapping("/completas/{usuarioId}")
    public ResponseEntity<List<Respiracao>> buscarSessoesCompletas(@PathVariable String usuarioId) {
        List<Respiracao> sessoes = respiracaoService.buscarSessaoCompleta(usuarioId);
        return ResponseEntity.ok(sessoes);
    }
    
    // Sessões de hoje
    @GetMapping("/hoje/{usuarioId}")
    public ResponseEntity<List<Respiracao>> buscarSessoesHoje(@PathVariable String usuarioId) {
        List<Respiracao> sessoes = respiracaoService.buscarSessaoDoDia(usuarioId);
        return ResponseEntity.ok(sessoes);
    }
    
    // Estatísticas do usuário
    @GetMapping("/estatisticas/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obterEstatisticas(@PathVariable String usuarioId) {
        Map<String, Object> estatisticas = respiracaoService.obterEstastistica(usuarioId);
        return ResponseEntity.ok(estatisticas);
    }

    
}
