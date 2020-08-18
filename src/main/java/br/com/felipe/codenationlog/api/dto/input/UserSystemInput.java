package br.com.felipe.codenationlog.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSystemInput {

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  private String password;
}