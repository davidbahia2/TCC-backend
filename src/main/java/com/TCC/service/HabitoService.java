package com.TCC.service;

import com.TCC.model.Habito;
import com.TCC.repository.HabitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    public Habito criarHabito(Habito habito) {
        if (habito.getDataCriacao() == null) {
            habito.setDataCriacao(LocalDateTime.now());
        }
        if (habito.getConcluida() == null) {
            habito.setConcluida(false);
        }
        return habitoRepository.save(habito);
    }

    public List<Habito> buscarHabitosPorUsuario(Integer usuarioId) {
        return habitoRepository.findByUsuario_Id(usuarioId);
    }

    public Habito buscarPorId(Integer id) {
        return habitoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));
    }

    public Habito atualizarHabito(Integer id, Habito habitoAtualizado) {
        Habito habitoExistente = buscarPorId(id);
        habitoExistente.setTituloHabito(habitoAtualizado.getTituloHabito());
        habitoExistente.setHorario(habitoAtualizado.getHorario());
        habitoExistente.setDiaSemana(habitoAtualizado.getDiaSemana());
        return habitoRepository.save(habitoExistente);
    }

    public Habito marcarComoConcluido(Integer id) {
        Habito habito = buscarPorId(id);
        habito.setConcluida(true);
        habito.setDataConclusao(LocalDateTime.now());
        return habitoRepository.save(habito);
    }

    public Habito desmarcarConcluido(Integer id) {
        Habito habito = buscarPorId(id);
        habito.setConcluida(false);
        habito.setDataConclusao(null);
        return habitoRepository.save(habito);
    }

    public void deletarHabito(Integer id) {
        habitoRepository.deleteById(id);
    }

    public List<Habito> buscarHabitosPorDia(Integer usuarioId, String diaSemana) {
        return habitoRepository.findByUsuario_IdAndDiaSemana(usuarioId, diaSemana);
    }

    public List<Habito> buscarHabitosNaoConcluidos(Integer usuarioId) {
        return habitoRepository.findByUsuario_IdAndConcluidaFalse(usuarioId);
    }

    public List<Habito> buscarHabitosConcluidos(Integer usuarioId) {
        return habitoRepository.findByUsuario_IdAndConcluida(usuarioId, true);
    }

    public Map<String, Object> calcularEstatisticasSemana(Integer usuarioId) {
        LocalDateTime inicioSemana = LocalDateTime.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .withHour(0).withMinute(0).withSecond(0);

        LocalDateTime fimSemana = LocalDateTime.now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .withHour(23).withMinute(59).withSecond(59);

        Map<String, Object> estatisticas = new HashMap<>();

        Long habitosConcluidos = habitoRepository.contarHabitosConcluidosSemana(usuarioId, inicioSemana);

        Double taxaConclusao = habitoRepository.calcularTaxaConclusaoSemana(usuarioId, inicioSemana);

        List<Habito> todosHabitos = habitoRepository.findByUsuario_Id(usuarioId);

        List<Habito> habitosConcluidosSemana = habitoRepository
            .findByUsuario_IdAndConcluidaTrueAndDataConclusaoBetween(usuarioId, inicioSemana, fimSemana);

        estatisticas.put("habitosConcluidos", habitosConcluidos);
        estatisticas.put("taxaConclusao", taxaConclusao != null ? Math.round(taxaConclusao * 10.0) / 10.0 : 0.0);
        estatisticas.put("totalHabitos", todosHabitos.size());
        estatisticas.put("habitosConcluidosDetalhes", habitosConcluidosSemana);

        return estatisticas;
    }

    public Map<String, Object> buscarProgressoHabitos(Integer usuarioId) {
        List<Habito> todosHabitos = habitoRepository.findByUsuario_Id(usuarioId);
        List<Habito> habitosConcluidos = habitoRepository.findByUsuario_IdAndConcluida(usuarioId, true);

        Map<String, Object> progresso = new HashMap<>();
        progresso.put("total", todosHabitos.size());
        progresso.put("concluidos", habitosConcluidos.size());

        double percentual = todosHabitos.isEmpty() ? 0.0 :
            (habitosConcluidos.size() * 100.0) / todosHabitos.size();
        progresso.put("percentual", Math.round(percentual * 10.0) / 10.0);

        return progresso;
    }
}
