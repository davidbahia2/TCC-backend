package com.TCC.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TCC.DTO.HabitoDTO;
import com.TCC.model.Habito;
import com.TCC.repository.HabitoRepository;

@Service
@Transactional
public class HabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    // cria habito
    public Habito adicionarHabito(Habito habito) {
        habito.setTituloHabito(habito.getTituloHabito());
        habito.setHorario(habito.getHorario());
        habito.setDiaSemana(habito.getDiaSemana());
        habito.setDataCriacao(habito.getDataCriacao());

        return habitoRepository.save(habito);
    }

    // buscar por habito
    public List<Habito> buscarTodosHabitos() {
        return habitoRepository.findAll();
    }

    // busca por dia da semana
    public List<Habito> buscarHabitoPorDia(DayOfWeek diaSemana) {
        return habitoRepository.buscarPorDia(diaSemana);
    }

//busca o habito atual
    public List<Habito> buscarHabitoAgora() {
        DayOfWeek hoje = LocalDateTime.now().getDayOfWeek();
        return habitoRepository.buscarPorDia(hoje);
    }


    public Map<DayOfWeek ,Map<String, Long>> contabilizadorPordia(){
        Map<DayOfWeek, Map<String,Long>> resultado = new HashMap<>();
        for(DayOfWeek dia: DayOfWeek.values()){
         

        Long totalHabito = habitoRepository.contaPordia(dia);
        Long HabitoConcluido = habitoRepository.contaConcluidaPorSemana(dia);

        Map<String,Long> grafico = new HashMap<>();
        grafico.put("total", totalHabito);
        grafico.put("concluido", HabitoConcluido);
        grafico.put("pendentes", totalHabito - HabitoConcluido);
            
        resultado.put(dia, grafico);
        }
        return resultado;
    }
    public Map<DayOfWeek, Double> calculaPorcetagemConclusao(){
        Map<DayOfWeek, Double> percentuais = new HashMap<>();

        for(DayOfWeek dia: DayOfWeek.values()){
            Long total = habitoRepository.contaPordia(dia);
            Long concluidas = habitoRepository.contaConcluidaPorSemana(dia);

            Double percentual = total > 0 ?(concluidas.doubleValue()/ total.doubleValue())* 100:0;
            percentuais.put(dia, percentual);
        }
        return percentuais;

    }
    // marca como concluido o habito
    public Habito concluirHabito(String HabitoId) {
        Optional<Habito> OptHabito = habitoRepository.findById(HabitoId);

        if (OptHabito.isPresent()) {
            Habito habito = OptHabito.get();
            habito.setConcluida(true);
            habito.setDataConclusao(LocalDateTime.now());

            return habitoRepository.save(habito);
        }

        throw new RuntimeException("Habito nao encontrado com Id" + HabitoId);

    }

    // desmarca o Habito
    public Habito desconcluirHabito(String HabitoId) {
        Optional<Habito> optHabito = habitoRepository.findById(HabitoId);

        if (optHabito.isPresent()) {
            Habito habito = optHabito.get();
            habito.setConcluida(false);
            habito.setDataConclusao(null);
            return habitoRepository.save(habito);
        }

        throw new RuntimeException("Tarefa não encontrada com ID: " + HabitoId);
    }

    public Habito atualizaHabito(String id, Habito habito) {
        Optional<Habito> optHabito = habitoRepository.findById(id);

        if (optHabito.isPresent()) {
            Habito habitoExistente = optHabito.get();
            habitoExistente.setTituloHabito(habito.getTituloHabito());
            habitoExistente.setHorario(habito.getHorario());
            habitoExistente.setDiaSemana(habito.getDiaSemana());
            return habitoRepository.save(habitoExistente);
        }
        throw new RuntimeException("Habito nao encontrado pelo id " + id);

    }

    public void deletarHabito(String id) {
        if (!habitoRepository.existsById(id)) {
            throw new RuntimeException("Habito nao encontrado pelo id");
        }
        habitoRepository.deleteById(id);
    }

}
