package com.TCC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.DTO.AuthenticationDTO;
import com.TCC.DTO.LoginResponseDTO;
import com.TCC.DTO.RegisterDTO;
import com.TCC.model.Usuario;
import com.TCC.repository.UsuarioRepository;
import com.TCC.security.TokenService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
            var UsernamePassword = new UsernamePasswordAuthenticationToken(data.nome(), data.senha());
            var auth = this.authenticationManager.authenticate(UsernamePassword);   
            var token = tokenService.generateToken((Usuario)auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity Cadastro (@RequestBody @Valid RegisterDTO data) {
            if(this.usuarioRepository.findByNome(data.nome()) != null)  return ResponseEntity.badRequest().build();

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
            Usuario newUsuario = new Usuario(data.nome(), encryptedPassword, data.role());

            this.usuarioRepository.save(newUsuario);
            return ResponseEntity.ok().build();
    }
    
}

