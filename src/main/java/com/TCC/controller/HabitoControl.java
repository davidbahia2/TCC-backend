package com.TCC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.service.HabitoService;



@RestController
@RequestMapping("habito")
public class HabitoControl {
    
private final HabitoService habitoService;

public HabitoControl (HabitoService habitoService){
    this.habitoService = habitoService;
}

}
