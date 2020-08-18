package br.com.felipe.codenationlog.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipe.codenationlog.domain.model.UserSystem;

public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {

}