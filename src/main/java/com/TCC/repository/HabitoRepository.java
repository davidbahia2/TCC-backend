package com.TCC.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TCC.model.Habito;

public interface HabitoRepository extends MongoRepository <Habito, String > {
    
}
