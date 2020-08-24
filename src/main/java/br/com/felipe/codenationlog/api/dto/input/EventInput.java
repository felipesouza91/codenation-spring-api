package br.com.felipe.codenationlog.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.felipe.codenationlog.domain.model.LogLevel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventInput {

  @NotBlank
  @NotNull
  @ApiParam(value = "description of event", example = "description of an events")
  private String eventDescription;

  @NotNull
  @ApiParam(value = "Type of event", example = "INFO")
  private LogLevel level;

  @NotBlank
  @NotNull
  @ApiParam(value = "Information of events", example = "Events information")
  private String eventLog;

  @NotNull
  @ApiParam(value = "Quantity of a event", example = "1")
  private Integer quantity;
}