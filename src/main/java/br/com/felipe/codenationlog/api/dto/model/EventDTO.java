package br.com.felipe.codenationlog.api.dto.model;

import java.time.LocalDateTime;

import br.com.felipe.codenationlog.domain.model.LogLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {

  private Long id;

  private String eventDescription;

  private LogLevel level;

  private String eventLog;

  private UserSystemDTO origin;

  private LocalDateTime eventDate;

  private Integer quantity;

}