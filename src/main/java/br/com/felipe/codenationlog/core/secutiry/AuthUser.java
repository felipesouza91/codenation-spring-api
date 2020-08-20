package br.com.felipe.codenationlog.core.secutiry;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.felipe.codenationlog.domain.model.UserSystem;
import lombok.Getter;

@Getter
public class AuthUser extends User {

  private static final long serialVersionUID = 1L;

  private UserSystem usuario;
  private Long id;
  private String fullName;

  public AuthUser(UserSystem usuario, Collection<GrantedAuthority> collection) {
    super(usuario.getUsername(), usuario.getPassword(), collection);
    this.fullName = usuario.getName();
    this.id = usuario.getId();
    this.usuario = usuario;
  }

}