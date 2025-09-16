package com.TCC.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.TCC.repository.SuporteRepository;
import org.springframework.stereotype.Service;

@Service
public class SuporteService {
    

@Autowired
    private final SuporteRepository suporteRepository;

    public SuporteService (SuporteRepository suporteRepository){
        this.suporteRepository = suporteRepository;
    }
}
