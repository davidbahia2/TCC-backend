package com.TCC.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TCC.model.Diario;
public interface DiarioRepository extends MongoRepository <Diario,String> {

     List<Diario> findByUserId(String userId);

    List<Diario> findByUserIdAndDataCriacaoAfter(String userId, LocalDateTime data);

}
