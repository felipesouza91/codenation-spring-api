package br.com.felipe.codenationlog.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipe.codenationlog.domain.model.UserSystem;

public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {

	Optional<UserSystem> findByUsername(String username);

}