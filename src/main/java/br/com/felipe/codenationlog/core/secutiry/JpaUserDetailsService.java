package br.com.felipe.codenationlog.core.secutiry;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.felipe.codenationlog.domain.model.UserSystem;
import br.com.felipe.codenationlog.infra.repository.UserSystemRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  @Autowired
  private UserSystemRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserSystem usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha invalida"));

    return new AuthUser(usuario, Collections.emptyList());
  }
}