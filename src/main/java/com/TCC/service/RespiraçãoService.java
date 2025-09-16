package com.TCC.service;

import java.time.LocalDateTime;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;

import com.TCC.model.Respiracao;
import com.TCC.repository.RespiracaoRepository;
import org.springframework.stereotype.Service;

@Service
public class RespiraçãoService {

@Autowired
    private final RespiracaoRepository respiracaoRepository;

    public RespiraçãoService(RespiracaoRepository respiracaoRepository) {
        this.respiracaoRepository = respiracaoRepository;
    }

    public Respiracao iniciar(String id) {

        Respiracao exercicio = respiracaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("exercicio nao encontrado"));

        exercicio.setStatus(Respiracao.Status.EM_ANDAMENTO);
        exercicio.setInicio(LocalDateTime.now());
        exercicio.setCicloAtual(1);

        return respiracaoRepository.save(exercicio);

    }

    public Respiracao pausar(String id) {

        Respiracao exercicio = respiracaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("exercicio nao encontrado"));
        exercicio.setStatus(Respiracao.Status.PAUSADO);
        return respiracaoRepository.save(exercicio);

    }

    public Respiracao reniciar(String id) {
        Respiracao exercicio = respiracaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("exercicio nao encontra"));

        exercicio.setStatus(Respiracao.Status.PRONTO);
        exercicio.setCicloAtual(0);
        exercicio.setInicio(null);
        exercicio.setFim(null);

        return respiracaoRepository.save(exercicio);

    }

    public Respiracao finalizar(String id) {
        Respiracao exercicio = respiracaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("nao encontrado"));

        exercicio.setStatus(Respiracao.Status.FINALIZADO);
        exercicio.setFim(LocalDateTime.now());

        return respiracaoRepository.save(exercicio);

    }

}
