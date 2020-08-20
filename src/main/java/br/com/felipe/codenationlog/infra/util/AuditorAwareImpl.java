package br.com.felipe.codenationlog.infra.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.felipe.codenationlog.domain.model.UserSystem;
import br.com.felipe.codenationlog.infra.repository.UserSystemRepository;

@Service
public class AuditorAwareImpl implements AuditorAware<UserSystem> {

  @Autowired
  private UserSystemRepository usuarioRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Optional<UserSystem> getCurrentAuditor() {
    var username = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("username")
        .toString();

    return usuarioRepository.findByUsername(username);
  }

}