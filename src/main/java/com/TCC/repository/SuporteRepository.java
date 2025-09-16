package com.TCC.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TCC.model.Suporte;

public interface SuporteRepository extends MongoRepository <Suporte, String> {
    
}
