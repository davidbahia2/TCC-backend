package com.TCC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioControl {
    

private final UsuarioService usuarioService;

public UsuarioControl(UsuarioService usuarioService){
    this.usuarioService = usuarioService;
}





}
