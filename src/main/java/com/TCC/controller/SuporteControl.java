package com.TCC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.service.SuporteService;
    
@RestController
@RequestMapping("suporte")
public class SuporteControl {

private final SuporteService suporteService;

public SuporteControl(SuporteService suporteService){
    this.suporteService = suporteService;
}




}
