package com.TCC.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.model.Suporte;
import com.TCC.service.SuporteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("suporte")
public class SuporteControl {

    private final SuporteService suporteService;

    public SuporteControl(SuporteService suporteService) {
        this.suporteService = suporteService;
    }

    @PostMapping("/criarSuporte")
    public ResponseEntity<Suporte> criarSuporte(@RequestBody Suporte suporte) {
        try {
            Suporte suportes = suporteService.CriarSuporte(suporte);
            return ResponseEntity.status(HttpStatus.CREATED).body(suportes);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/buscarCity")
    public ResponseEntity<List<Suporte>> buscarCity(@PathVariable String cidades) {
        List<Suporte> suportes = suporteService.buscarPorCidade(cidades);
        return ResponseEntity.ok(suportes);
    }

    @GetMapping("/buscarEspeciarias")
    public ResponseEntity<List<Suporte>> buscarEspeciarias(@RequestParam String especialidades) {
        List<Suporte> suportes = suporteService.buscarPorEspecialidade(especialidades);
        return ResponseEntity.ok(suportes);
    }

@GetMapping("/buscarPorCidadeEEspecialidade") 
    public ResponseEntity<List<Suporte>> buscarOsDois(@RequestParam String especialidades, String cidades) {
        List<Suporte> suportes = suporteService.buscarOsDois(especialidades, cidades);
        return ResponseEntity.ok(suportes);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Suporte>> buscarTodos() {
        List<Suporte> suportes = suporteService.listarTudo();
        return ResponseEntity.ok(suportes);
    }

    @PutMapping("atualiza/{id}")
    public ResponseEntity AtualizaSup(@PathVariable int id, @RequestBody Suporte suporte) {

        try {
            Suporte sup = suporteService.atualizar(suporte, id);
            return ResponseEntity.ok(sup);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        try {

            suporteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
