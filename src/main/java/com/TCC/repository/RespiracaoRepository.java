package com.TCC.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TCC.model.Respiracao;

public interface RespiracaoRepository extends MongoRepository <Respiracao, String> {

    
    
}
