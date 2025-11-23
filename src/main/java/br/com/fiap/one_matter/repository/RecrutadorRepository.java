package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Recrutador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecrutadorRepository extends JpaRepository<Recrutador, Long> {
    Page<Recrutador> findByDeleted(Integer deleted, Pageable pageable);
    Optional<Recrutador> findByIdAndDeleted(Long id, Integer deleted);
}