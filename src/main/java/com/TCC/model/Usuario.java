package com.TCC.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.TCC.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Document(collection = "Usuarios")
public class Usuario implements UserDetails{
    

@Id
private String id;
@NotBlank(message = "campo obrigatorio")
private String email;
@NotBlank(message = "campo obrigatorio")
private String nome;
@NotBlank(message = "campo obrigatorio")
private String senha;
private UserRole role;


@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("role_ADMIN"), new SimpleGrantedAuthority("role_USUARIO"));
    else return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
}

@Override
public String getPassword() {
    // TODO Auto-generated method stub
    return this.getSenha();
}
@Override
public String getUsername() {
    // TODO Auto-generated method stub
    return this.getNome();
} 


}



