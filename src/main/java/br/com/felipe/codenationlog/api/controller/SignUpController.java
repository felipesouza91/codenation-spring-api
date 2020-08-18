package br.com.felipe.codenationlog.api.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.felipe.codenationlog.api.dto.converter.UserSystemDtoManager;
import br.com.felipe.codenationlog.api.dto.input.UserSystemInput;
import br.com.felipe.codenationlog.api.dto.model.UserSystemDTO;
import br.com.felipe.codenationlog.domain.model.UserSystem;
import br.com.felipe.codenationlog.domain.service.UserSystemService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/signup")
public class SignUpController {

  @Autowired
  private UserSystemService userSystemService;

  @Autowired
  private UserSystemDtoManager dtoManager;

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public UserSystemDTO create(@RequestBody @Valid UserSystemInput user) {
    UserSystem userSave = userSystemService.save(dtoManager.toDomainObject(user));
    return dtoManager.toModel(userSave);
  }

}