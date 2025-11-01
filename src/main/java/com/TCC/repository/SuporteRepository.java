package com.TCC.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TCC.model.Suporte;

public interface SuporteRepository extends JpaRepository<Suporte, Integer> {

    @Query("SELECT s FROM Suporte s WHERE s.cidade = :cidade")
    List<Suporte> procurarPorCidade(@Param("cidade") String cidade);

    @Query("SELECT s FROM Suporte s WHERE s.especialidade = :especialidade")
    List<Suporte> procurarPorEspecialidade(@Param("especialidade") String especialidade);

    @Query("""
        SELECT s FROM Suporte s
        WHERE s.cidade = :cidade
          AND s.especialidade = :especialidade
    """)
    List<Suporte> procurarPelosDois(@Param("cidade") String cidade,
                                    @Param("especialidade") String especialidade);
}
