package com.TCC.controller;

import com.TCC.DTO.HabitoDTO;
import com.TCC.model.Habito;
import com.TCC.service.HabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habito")
@CrossOrigin(origins = "*")
public class HabitoControl {

    @Autowired
    private HabitoService habitoService;

    // Criar novo hábito
   @PostMapping
public ResponseEntity<HabitoDTO> criarHabito(@RequestBody Habito habito) {
    try {
        Habito novoHabito = habitoService.criarHabito(habito);
        HabitoDTO resposta = HabitoDTO.fromEntity(novoHabito);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}


    // Buscar todos os hábitos de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Habito>> buscarHabitosUsuario(@PathVariable Integer usuarioId) {
        try {
            List<Habito> habitos = habitoService.buscarHabitosPorUsuario(usuarioId);
            return ResponseEntity.ok(habitos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar hábito específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Habito> buscarPorId(@PathVariable Integer id) {
        try {
            Habito habito = habitoService.buscarPorId(id);
            return ResponseEntity.ok(habito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Atualizar hábito
    @PutMapping("/{id}")
    public ResponseEntity<Habito> atualizarHabito(
            @PathVariable Integer id,
            @RequestBody Habito habito) {
        try {
            Habito habitoAtualizado = habitoService.atualizarHabito(id, habito);
            return ResponseEntity.ok(habitoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Marcar hábito como concluído
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Habito> marcarComoConcluido(@PathVariable Integer id) {
        try {
            Habito habitoConcluido = habitoService.marcarComoConcluido(id);
            return ResponseEntity.ok(habitoConcluido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Desmarcar hábito como concluído
    @PatchMapping("/{id}/desconcluir")
    public ResponseEntity<Habito> desmarcarConcluido(@PathVariable Integer id) {
        try {
            Habito habitoDesconcluido = habitoService.desmarcarConcluido(id);
            return ResponseEntity.ok(habitoDesconcluido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Deletar hábito
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarHabito(@PathVariable Integer id) {
        try {
            habitoService.deletarHabito(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Buscar hábitos por dia da semana
    @GetMapping("/usuario/{usuarioId}/dia/{diaSemana}")
    public ResponseEntity<List<Habito>> buscarHabitosPorDia(
            @PathVariable Integer usuarioId,
            @PathVariable String diaSemana) {
        try {
            List<Habito> habitos = habitoService.buscarHabitosPorDia(usuarioId, diaSemana);
            return ResponseEntity.ok(habitos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar hábitos não concluídos
    @GetMapping("/usuario/{usuarioId}/nao-concluidos")
    public ResponseEntity<List<Habito>> buscarHabitosNaoConcluidos(@PathVariable Integer usuarioId) {
        try {
            List<Habito> habitos = habitoService.buscarHabitosNaoConcluidos(usuarioId);
            return ResponseEntity.ok(habitos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar hábitos concluídos
    @GetMapping("/usuario/{usuarioId}/concluidos")
    public ResponseEntity<List<Habito>> buscarHabitosConcluidos(@PathVariable Integer usuarioId) {
        try {
            List<Habito> habitos = habitoService.buscarHabitosConcluidos(usuarioId);
            return ResponseEntity.ok(habitos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar estatísticas dos hábitos da semana
    @GetMapping("/usuario/{usuarioId}/estatisticas-semana")
    public ResponseEntity<Map<String, Object>> buscarEstatisticasSemana(@PathVariable Integer usuarioId) {
        try {
            Map<String, Object> estatisticas = habitoService.calcularEstatisticasSemana(usuarioId);
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar progresso geral dos hábitos
    @GetMapping("/usuario/{usuarioId}/progresso")
    public ResponseEntity<Map<String, Object>> buscarProgressoHabitos(@PathVariable Integer usuarioId) {
        try {
            Map<String, Object> progresso = habitoService.buscarProgressoHabitos(usuarioId);
            return ResponseEntity.ok(progresso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
