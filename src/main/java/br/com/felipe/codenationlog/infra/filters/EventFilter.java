package br.com.felipe.codenationlog.infra.filters;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiParam;
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
  @ApiParam(format = "yyyy-MM-dd", value = "Date when event was creat to start filter", example = "2020-02-12")
  private LocalDate dateStart;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ApiParam(format = "yyyy-MM-dd", value = "Date when event was creat to start filter", example = "2020-02-12")
  private LocalDate dateEnd;
}