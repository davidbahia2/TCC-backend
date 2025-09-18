package com.TCC.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TCC.model.Diario;
public interface DiarioRepository extends MongoRepository <Diario,String> {

     List<Diario> ProcuraPorId(String userId);

    List<Diario> procuraPorIdData(String userId, LocalDateTime data);

    long contaPorIdData(String userId, LocalDateTime dataCriacao);


    long contaPorId(String userId);
}
