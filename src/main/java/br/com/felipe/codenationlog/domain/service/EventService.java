package br.com.felipe.codenationlog.domain.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.felipe.codenationlog.domain.model.Event;
import br.com.felipe.codenationlog.infra.filters.EventFilter;
import br.com.felipe.codenationlog.infra.repository.EventRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  public Page<Event> findAll(Pageable pageable, EventFilter filter) {

    return eventRepository.findAll(pageable, filter);
  }

  public Event findById(Long id) {
    return eventRepository.findById(id).orElseThrow();
  }

  public Event save(@Valid Event event) {
    return eventRepository.save(event);
  }

  public void remove(Long id) {
    this.findById(id);
    this.eventRepository.deleteById(id);
  }
}