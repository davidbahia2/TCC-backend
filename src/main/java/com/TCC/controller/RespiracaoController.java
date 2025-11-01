package com.TCC.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.TCC.DTO.*;
import com.TCC.model.Respiracao;
import com.TCC.model.Usuario;
import com.TCC.repository.UsuarioRepository;
import com.TCC.service.RespiracaoService;

@RestController
@RequestMapping("/respirar")
public class RespiracaoController {

    @Autowired
    private RespiracaoService respiracaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getUsuarioAutenticado() {
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(nomeUsuario);
        return usuarioOpt.orElse(null);
    }

    @GetMapping("/configuracoes")
    public ResponseEntity<List<ConfiguracaoRespiracaoDTO>> obterConfiguracoes() {
        return ResponseEntity.ok(respiracaoService.obterConfig());
    }

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarSessao(@RequestBody IniciarSessaoDTO dto) {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            Respiracao sessao = respiracaoService.inicio(dto, usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping("/{sessaoId}/finalizar")
    public ResponseEntity<?> finalizarSessao(@PathVariable int sessaoId, @RequestBody FinalizarSessaoDTO dto) {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            Respiracao sessao = respiracaoService.finalizar(sessaoId, dto);
            return ResponseEntity.ok(sessao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/ativa")
    public ResponseEntity<?> buscarSessaoAtiva() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            return respiracaoService.buscaSessaoAtiva(usuario.getId().intValue())
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("mensagem", "Nenhuma sessão ativa encontrada")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/cancelar")
    public ResponseEntity<?> cancelarSessaoAtiva() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            respiracaoService.cancelarSessaoAtiva(usuario.getId().intValue());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/historico")
    public ResponseEntity<?> buscarHistorico() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            return ResponseEntity.ok(respiracaoService.buscarHistorico(usuario.getId().intValue()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/completas")
    public ResponseEntity<?> buscarSessoesCompletas() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            return ResponseEntity.ok(respiracaoService.buscarSessaoCompleta(usuario.getId().intValue()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/hoje")
    public ResponseEntity<?> buscarSessoesHoje() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            return ResponseEntity.ok(respiracaoService.buscarSessaoDoDia(usuario.getId().intValue()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<?> obterEstatisticas() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Usuário não encontrado"));
        }

        try {
            return ResponseEntity.ok(respiracaoService.obterEstatistica(usuario.getId().intValue()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}
