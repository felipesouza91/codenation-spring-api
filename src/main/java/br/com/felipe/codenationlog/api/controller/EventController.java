package br.com.felipe.codenationlog.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipe.codenationlog.api.dto.converter.EventDtoManager;
import br.com.felipe.codenationlog.api.dto.input.EventInput;
import br.com.felipe.codenationlog.api.dto.model.EventDTO;
import br.com.felipe.codenationlog.domain.model.Event;
import br.com.felipe.codenationlog.domain.service.EventService;
import br.com.felipe.codenationlog.infra.filters.EventFilter;

@RestController
@RequestMapping("/events")
public class EventController {

  @Autowired
  private EventService eventService;

  @Autowired
  private EventDtoManager dtoManager;

  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public Page<Event> findAll(Pageable pageable, EventFilter filter) {
    return eventService.findAll(pageable, filter);
  }

  @GetMapping("/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public EventDTO findOne(@PathVariable Long id) {
    Event eventFind = eventService.findById(id);
    return dtoManager.toModel(eventFind);
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public EventDTO save(@RequestBody @Valid EventInput event) {
    Event eventSave = eventService.save(dtoManager.toDomainObject(event));
    return dtoManager.toModel(eventSave);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void remove(@PathVariable Long id) {
    this.eventService.remove(id);
  }
}