package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Page<Vaga> findByDeleted(Integer deleted, Pageable pageable);
    Optional<Vaga> findByIdAndDeleted(Long id, Integer deleted);
}