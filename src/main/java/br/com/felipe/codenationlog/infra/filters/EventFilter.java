package br.com.felipe.codenationlog.infra.filters;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventFilter {

  private String eventDescription;

  private String level;

  private String eventLog;

  private Long systemId;

  private Integer quantity;
  @DateTimeFormat(pattern = "yyyy-MM-dd")

  private LocalDate dateStart;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateEnd;
}