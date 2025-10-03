package com.TCC.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TCC.model.Diario;
import com.TCC.repository.DiarioRepository;
@Service
public class DiarioService {

    @Autowired
    private final DiarioRepository diarioRepository;

    public DiarioService(DiarioRepository diarioRepository) {
        this.diarioRepository = diarioRepository;
    }

    public Diario criaDiario(Diario diario) {
        diario.setDataCriacao(LocalDateTime.now());
        return diarioRepository.save(diario);
    }

public List<Diario> buscarDiaroPorUsuario(int userId){
    return diarioRepository.procuraPorId(userId);  // ✅ passa o userId
}
    public double calcularHumorMedioDaSemana(int userId) {

        LocalDateTime umaSemanaAtras = LocalDateTime.now().minusWeeks(1);
        List<Diario> entradasDaSemana = diarioRepository.procuraPorIdData(userId, umaSemanaAtras);

        if (entradasDaSemana.isEmpty()) {
            return 0.0;
        }

        double somaHumores = entradasDaSemana.stream()
                .mapToDouble(entrada -> {
                    switch (entrada.getHumor()) {
                        case "Ótimo":
                            return 5.0;
                        case "Bem":
                            return 4.0;
                        case "Normal":
                            return 3.0;
                        case "Baixo":
                            return 2.0;
                        case "Ruim":
                            return 1.0;
                        default:
                            return 0.0;
                    }
                })
                .sum();

        return somaHumores / entradasDaSemana.size();
    }

        public long contarEntradasDaSemana(int userId) {
        LocalDateTime umaSemanaAtras = LocalDateTime.now().minusWeeks(1);
        return diarioRepository.contaPorIdData(userId, umaSemanaAtras);
    }
    
      public long contarTotalDeEntradas(int userId) {
        return diarioRepository.contaPorId(userId);
    }
}
