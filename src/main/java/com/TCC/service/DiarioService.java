package com.TCC.service;

import com.TCC.model.Diario;
import com.TCC.repository.DiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiarioService {
    
    @Autowired
    private DiarioRepository diarioRepository;
    
    // Criar nova entrada no diário
    public Diario criarEntrada(Diario diario) {
        if (diario.getDataCriacao() == null) {
            diario.setDataCriacao(LocalDateTime.now());
        }
        return diarioRepository.save(diario);
    }
    
    // Buscar todas as entradas de um usuário
    public List<Diario> buscarEntradasPorUsuario(Integer usuarioId) {
        return diarioRepository.findByUsuario_IdOrderByDataCriacaoDesc(usuarioId);
    }
    
    // Buscar entrada por ID
    public Diario buscarPorId(Integer id) {
        return diarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
    }
    
    // Atualizar entrada
    public Diario atualizarEntrada(Integer id, Diario diarioAtualizado) {
        Diario diarioExistente = buscarPorId(id);
        diarioExistente.setHumor(diarioAtualizado.getHumor());
        diarioExistente.setDescricaoPensamentos(diarioAtualizado.getDescricaoPensamentos());
        return diarioRepository.save(diarioExistente);
    }
    
    // Deletar entrada
    public void deletarEntrada(Integer id) {
        diarioRepository.deleteById(id);
    }
    
    // Buscar entradas da semana atual
    public List<Diario> buscarEntradasSemanaAtual(Integer usuarioId) {
        LocalDateTime inicioSemana = LocalDateTime.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .withHour(0).withMinute(0).withSecond(0);
        
        LocalDateTime fimSemana = LocalDateTime.now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .withHour(23).withMinute(59).withSecond(59);
        
        return diarioRepository.findByUsuario_IdAndDataCriacaoBetween(usuarioId, inicioSemana, fimSemana);
    }
    
    // Calcular estatísticas do humor da semana
    public Map<String, Object> calcularEstatisticasSemana(Integer usuarioId) {
        LocalDateTime inicioSemana = LocalDateTime.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .withHour(0).withMinute(0).withSecond(0);
        
        Map<String, Object> estatisticas = new HashMap<>();
        
        // Buscar entradas da semana
        List<Diario> entradasSemana = buscarEntradasSemanaAtual(usuarioId);
        
        // Calcular humor médio
        Double humorMedio = diarioRepository.calcularHumorMedioSemana(usuarioId, inicioSemana);
        
        // Contar entradas
        Long totalEntradas = diarioRepository.contarEntradasSemana(usuarioId, inicioSemana);
        
        // Encontrar humor mais frequente
        Map<String, Long> distribuicaoHumor = new HashMap<>();
        for (Diario entrada : entradasSemana) {
            String humor = entrada.getHumor();
            distribuicaoHumor.put(humor, distribuicaoHumor.getOrDefault(humor, 0L) + 1);
        }
        
        String humorMaisFrequente = distribuicaoHumor.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
        
        estatisticas.put("humorMedio", humorMedio != null ? Math.round(humorMedio * 10.0) / 10.0 : 0.0);
        estatisticas.put("totalEntradas", totalEntradas);
        estatisticas.put("humorMaisFrequente", humorMaisFrequente);
        estatisticas.put("distribuicaoHumor", distribuicaoHumor);
        estatisticas.put("entradasRecentes", entradasSemana);
        
        return estatisticas;
    }
    
    // Buscar entradas por período personalizado
    public List<Diario> buscarEntradasPorPeriodo(Integer usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        return diarioRepository.findByUsuario_IdAndDataCriacaoBetween(usuarioId, inicio, fim);
    }
    
    // Buscar entradas por humor específico
    public List<Diario> buscarPorHumor(Integer usuarioId, String humor) {
        return diarioRepository.findByUsuario_IdAndHumor(usuarioId, humor);
    }
}