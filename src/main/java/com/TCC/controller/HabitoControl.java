package com.TCC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.model.Habito;
import com.TCC.service.HabitoService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/habito")
public class HabitoControl {

    private final HabitoService habitoService;

    public HabitoControl(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Habito> criarHabito(@RequestBody Habito habito) {
        try {
            Habito habitos = habitoService.adicionarHabito(habito);
            return ResponseEntity.status(HttpStatus.CREATED).body(habitos);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/buscaTodos")
    public ResponseEntity<List<Habito>> buscarTodos() {
        List<Habito> habito = habitoService.buscarTodosHabitos();
        return ResponseEntity.ok(habito);
    }

    @GetMapping("/dia/{diaSemana}")
    public ResponseEntity<List<Habito>> buscaHabitoPorDia(@PathVariable DayOfWeek dia) {
        List<Habito> habitos = habitoService.buscarHabitoPorDia(dia);
        return ResponseEntity.ok(habitos);
    }
@GetMapping("/percentual/por-dia")
public ResponseEntity<Map<DayOfWeek,Map<String, Long>>> contabilizaHabitoPorDia() {
     Map<DayOfWeek, Map<String, Long>> graficos = habitoService.contabilizadorPordia();
     return ResponseEntity.ok(graficos);
}

    @GetMapping("/percentual/grafico")
    public ResponseEntity<Map<DayOfWeek, Double>> calculaConclusao() {
        Map<DayOfWeek, Double> percentuais = habitoService.calculaPorcetagemConclusao();
        return ResponseEntity.ok(percentuais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habito> atualizaHabito(@PathVariable int id, @RequestBody Habito habito) {
        try {
            Habito habitos = habitoService.atualizaHabito(id, habito);
            return ResponseEntity.ok(habitos);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<Void> deletaHabito(@PathVariable int id){
        try {
            habitoService.deletarHabito(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/desmarcar/{id}")
    public ResponseEntity<Habito> desmarcarHabito(@PathVariable int id) {
        try {
            Habito habitos = habitoService.desconcluirHabito(id);
            return ResponseEntity.ok(habitos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<Habito> marcarHabito(@PathVariable int id) {
        try {
            Habito habitos = habitoService.concluirHabito(id);
            return ResponseEntity.ok(habitos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
