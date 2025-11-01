package com.TCC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCC.DTO.AuhtenticationDTO;
import com.TCC.DTO.LoginResponseDTO;
import com.TCC.DTO.RegisterDTO;
import com.TCC.enums.UserRole;
import com.TCC.model.Usuario;
import com.TCC.repository.UsuarioRepository;
import com.TCC.security.TokenService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // =====================
    // =====================
    // LOGIN
    // =====================
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody @Valid AuhtenticationDTO data) {
    var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

    var authentication = authenticationManager.authenticate(authToken);

    // ⚠️ Pega o usuário autenticado corretamente
    var usuario = (Usuario) authentication.getPrincipal();

    // ✅ Agora passa o objeto Usuario para gerar o token
    var token = tokenService.generateToken(usuario);

    return ResponseEntity.ok(new LoginResponseDTO(token));
}

    // =====================
    // CADASTRAR
    // =====================
    @PostMapping("/cadastrar")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.email(),data.senha(), encryptedPassword, data.role());
        newUser.setRole(data.role());
        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
