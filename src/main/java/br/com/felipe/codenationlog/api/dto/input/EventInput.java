package br.com.felipe.codenationlog.api.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventInput {

  @NotBlank
  @NotNull
  private String eventDescription;

  @NotNull
  private String level;

  @NotBlank
  @NotNull
  private String eventLog;

  @NotNull
  private Integer quantity;
}