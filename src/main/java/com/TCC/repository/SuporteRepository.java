package com.TCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TCC.model.Suporte;

public interface SuporteRepository extends JpaRepository<Suporte, Integer> {

    // Busca por cidade
    @Query("SELECT s FROM Suporte s WHERE s.cidade = :cidade")
    List<Suporte> procurarPorCidade(@Param("cidade") String cidade);
    
    // Busca por especialidade
    @Query("SELECT s FROM Suporte s WHERE s.especialidade = :especialidade")
    List<Suporte> procurarPorEspecialidade(@Param("especialidade") String especialidade);
    
    // Busca por cidade E especialidade
    @Query("SELECT s FROM Suporte s WHERE s.cidade = :cidade AND s.especialidade = :especialidade")
    List<Suporte> procurarPelosDois(
        @Param("cidade") String cidade, 
        @Param("especialidade") String especialidade
    );

    // Busca por nome (parcial)
    @Query("SELECT s FROM Suporte s WHERE s.nome LIKE %:nome%")
    List<Suporte> findByNomeContaining(@Param("nome") String nome);
    
    // Busca por serviço
    @Query("SELECT s FROM Suporte s WHERE :servico MEMBER OF s.servicos")
    List<Suporte> findByServico(@Param("servico") String servico);
}