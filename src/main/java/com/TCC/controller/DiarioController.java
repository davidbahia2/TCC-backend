package com.TCC.controller;

import com.TCC.model.Diario;
import com.TCC.service.DiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diario")
@CrossOrigin(origins = "*")
public class DiarioController {
    
    @Autowired
    private DiarioService diarioService;
    
    /**
     * Criar nova entrada no diário
     * POST /api/diario
     * Body: { "usuarioId": 1, "humor": "4", "descricaoPensamentos": "..." }
     */
    @PostMapping
    public ResponseEntity<Diario> criarEntrada(@RequestBody Diario diario) {
        try {
            Diario novaEntrada = diarioService.criarEntrada(diario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaEntrada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Buscar todas as entradas de um usuário (ordenadas por data)
     * GET /api/diario/usuario/{userId}
     */
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Diario>> buscarEntradasUsuario(@PathVariable Integer userId) {
        try {
            List<Diario> entradas = diarioService.buscarEntradasPorUsuario(userId);
            return ResponseEntity.ok(entradas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Buscar entrada específica por ID
     * GET /api/diario/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Diario> buscarPorId(@PathVariable Integer id) {
        try {
            Diario diario = diarioService.buscarPorId(id);
            return ResponseEntity.ok(diario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Atualizar entrada existente
     * PUT /api/diario/{id}
     * Body: { "humor": "5", "descricaoPensamentos": "..." }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Diario> atualizarEntrada(
            @PathVariable Integer id, 
            @RequestBody Diario diario) {
        try {
            Diario diarioAtualizado = diarioService.atualizarEntrada(id, diario);
            return ResponseEntity.ok(diarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Deletar entrada
     * DELETE /api/diario/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEntrada(@PathVariable Integer id) {
        try {
            diarioService.deletarEntrada(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Buscar entradas da semana atual
     * GET /api/diario/usuario/{userId}/semana-atual
     */
    @GetMapping("/usuario/{userId}/semana-atual")
    public ResponseEntity<List<Diario>> buscarEntradasSemanaAtual(@PathVariable Integer userId) {
        try {
            List<Diario> entradas = diarioService.buscarEntradasSemanaAtual(userId);
            return ResponseEntity.ok(entradas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Buscar estatísticas do humor da semana
     * GET /api/diario/usuario/{userId}/estatisticas-semana
     * 
     * Retorna:
     * {
     *   "humorMedio": 4.2,
     *   "totalEntradas": 3,
     *   "humorMaisFrequente": "4",
     *   "distribuicaoHumor": { "4": 2, "5": 1 },
     *   "entradasRecentes": [...]
     * }
     */
    @GetMapping("/usuario/{userId}/estatisticas-semana")
    public ResponseEntity<Map<String, Object>> buscarEstatisticasSemana(@PathVariable Integer userId) {
        try {
            Map<String, Object> estatisticas = diarioService.calcularEstatisticasSemana(userId);
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Buscar entradas por período personalizado
     * GET /api/diario/usuario/{userId}/periodo?inicio=2025-01-01T00:00:00&fim=2025-01-31T23:59:59
     */
    @GetMapping("/usuario/{userId}/periodo")
    public ResponseEntity<List<Diario>> buscarEntradasPorPeriodo(
            @PathVariable Integer userId,
            @RequestParam String inicio,
            @RequestParam String fim) {
        try {
            LocalDateTime dataInicio = LocalDateTime.parse(inicio);
            LocalDateTime dataFim = LocalDateTime.parse(fim);
            List<Diario> entradas = diarioService.buscarEntradasPorPeriodo(userId, dataInicio, dataFim);
            return ResponseEntity.ok(entradas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Buscar entradas por humor específico
     * GET /api/diario/usuario/{userId}/humor/{humor}
     * Exemplo: /api/diario/usuario/1/humor/5
     */
    @GetMapping("/usuario/{userId}/humor/{humor}")
    public ResponseEntity<List<Diario>> buscarPorHumor(
            @PathVariable Integer userId,
            @PathVariable String humor) {
        try {
            List<Diario> entradas = diarioService.buscarPorHumor(userId, humor);
            return ResponseEntity.ok(entradas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}