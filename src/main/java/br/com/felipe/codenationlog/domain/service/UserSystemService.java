package br.com.felipe.codenationlog.domain.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipe.codenationlog.domain.model.UserSystem;
import br.com.felipe.codenationlog.infra.repository.UserSystemRepository;
import lombok.Getter;

@Service
public class UserSystemService {

  @Autowired
  @Getter
  private UserSystemRepository userRepository;

  public UserSystem save(@Valid UserSystem user) {
    return this.userRepository.save(user);
  }

  public UserSystem findById(Long id) {
    return this.userRepository.findById(id).orElseThrow();
  }
}