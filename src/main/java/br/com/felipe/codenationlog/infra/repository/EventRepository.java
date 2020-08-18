package br.com.felipe.codenationlog.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipe.codenationlog.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}