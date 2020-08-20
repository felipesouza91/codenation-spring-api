package br.com.felipe.codenationlog.infra.repository.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

import br.com.felipe.codenationlog.domain.model.Event;
import br.com.felipe.codenationlog.domain.model.LogLevel;
import br.com.felipe.codenationlog.infra.filters.EventFilter;
import br.com.felipe.codenationlog.infra.repository.EventRepositoryQuery;

public class EventRepositoryQueryImpl implements EventRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Event> findAll(Pageable pageable, EventFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
    Root<Event> root = criteria.from(Event.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);

    criteria.where(predicates);
    criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));
    TypedQuery<Event> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(filter));
  }

  private Predicate[] criarRestricoes(EventFilter filter, CriteriaBuilder builder, Root<Event> root) {
    Long id = ((Jwt) SecurityContextHolder.getContext()
      .getAuthentication().getPrincipal()).getClaim("usuario_id");
    List<Predicate> predicates = new ArrayList<>();
    predicates.add(builder.equal(root.get("origin").get("id"),
    id));
    if (!StringUtils.isEmpty(filter.getEventDescription())) {
      predicates.add(builder.like(builder.lower(root.get("eventDescription")),
          "%" + filter.getEventDescription().toLowerCase() + "%"));
    }
    if (!StringUtils.isEmpty(filter.getEventLog())) {
      predicates.add(builder.like(builder.lower(root.get("eventLog")), "%" + filter.getEventLog().toLowerCase() + "%"));
    }
    if (!StringUtils.isEmpty(filter.getLevel())) {
      predicates.add(builder.equal(builder.lower(root.get("level")), LogLevel.valueOf(filter.getLevel())));
    }
    if (filter.getQuantity() != null) {
      predicates.add(builder.equal(root.get("quantity"), filter.getQuantity()));
    }
    if (!StringUtils.isEmpty(filter.getDateStart())) {
      predicates.add(builder.greaterThanOrEqualTo(root.get("eventDate"),
          LocalDateTime.of(filter.getDateStart(), LocalTime.of(0, 0, 0))));
    }

    if (!StringUtils.isEmpty(filter.getDateEnd())) {
      predicates.add(builder.lessThanOrEqualTo(root.get("eventDate"),
          LocalDateTime.of(filter.getDateEnd(), LocalTime.of(23, 59, 59))));
    }
    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
    int paginaAtual = pageable.getPageNumber();
    int totalRegistrosPorPagina = pageable.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPorPagina);
  }

  private Long total(EventFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Event> root = criteria.from(Event.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}