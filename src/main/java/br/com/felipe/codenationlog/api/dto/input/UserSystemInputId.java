package br.com.felipe.codenationlog.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSystemInputId {

  @NotNull
  private Long id;
}
