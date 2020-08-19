package br.com.felipe.codenationlog.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.felipe.codenationlog.domain.model.Event;
import br.com.felipe.codenationlog.infra.filters.EventFilter;

public interface EventRepositoryQuery {

  public Page<Event> findAll(Pageable pageable, EventFilter filter);
}