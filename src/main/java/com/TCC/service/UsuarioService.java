package com.TCC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.TCC.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {


@Autowired
private final UsuarioRepository usuarioRepository;

public UsuarioService (UsuarioRepository usuarioRepository){
    this.usuarioRepository = usuarioRepository;
}








@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usuarioRepository.findByNome(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
}
    
}
