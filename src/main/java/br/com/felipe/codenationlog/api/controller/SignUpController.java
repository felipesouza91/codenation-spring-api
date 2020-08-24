package br.com.felipe.codenationlog.api.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.felipe.codenationlog.api.dto.converter.UserSystemDtoManager;
import br.com.felipe.codenationlog.api.dto.input.UserSystemInput;
import br.com.felipe.codenationlog.api.dto.model.UserSystemDTO;
import br.com.felipe.codenationlog.domain.model.UserSystem;
import br.com.felipe.codenationlog.domain.service.UserSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/signup")
@Api(tags = "SignUp")
public class SignUpController {

  @Autowired
  private UserSystemService userSystemService;

  @Autowired
  private UserSystemDtoManager dtoManager;

  @Autowired
  private PasswordEncoder passwordEncode;

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  @ApiOperation(value = "Create a new api account")
  public ResponseEntity<UserSystemDTO> create(@RequestBody @Valid UserSystemInput user) {
    if (userExists(user.getUsername())) {
      return ResponseEntity.badRequest().build();
    }
    user.setPassword(passwordEncode.encode(user.getPassword()));
    UserSystem userSave = userSystemService.save(dtoManager.toDomainObject(user));

    return ResponseEntity.status(HttpStatus.CREATED).body(dtoManager.toModel(userSave));
  }

  private boolean userExists(String username) {
    return userSystemService.getUserRepository().findByUsername(username).isPresent();
  }
}