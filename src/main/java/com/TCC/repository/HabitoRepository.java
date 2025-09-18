package com.TCC.repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.TCC.model.Habito;

public interface HabitoRepository extends MongoRepository<Habito, String> {

    List<Habito> buscarPorDia(DayOfWeek diaSemana);

    Long contaPordia(DayOfWeek diaSemana);

    List<Habito> buscarConcluidaPorSemana(DayOfWeek diaSemana);

    Long contaConcluidaPorSemana(DayOfWeek diaSemana);

    List<Habito> buscarNaoConcluidaSemana(DayOfWeek diaSemana);

    Long contaNaoConcluida(DayOfWeek diaSemana);

    @Query("{'titulo': {$regex : ?0, $options: 'i'}}")
    List<Habito> buscarPorTitulo(DayOfWeek diaSemana);

    @Query("{ 'dataConclusao': { $gte: ?0, $lte: ?1 } }")
    List<Habito> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

    List<Habito> buscarDiaSemanaNaoConcluida(DayOfWeek diaSemana);
}
