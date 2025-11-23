package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.StatusCandidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCandidaturaRepository extends JpaRepository<StatusCandidatura, Long> {
}