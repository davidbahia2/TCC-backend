package com.TCC.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.TCC.model.Usuario;
import com.TCC.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter  extends OncePerRequestFilter{

@Autowired
TokenService tokenService;

@Autowired
UsuarioRepository userrepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException{
      var token = this.recoverToken(request);
      if (token != null) {
        var subject = tokenService.validadeToken(token);
        UserDetails User= userrepository.findByNome(subject);
        var authentication = new UsernamePasswordAuthenticationToken(User, null, User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

      }
      filterChain.doFilter(request, response);

    }


      private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("authorization");
        if (authHeader == null) return null;
        return authHeader.replace("bearer", "");
}
}
