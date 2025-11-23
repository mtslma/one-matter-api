package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, Long> {
    List<Formacao> findByCandidatoId(Long candidatoId);
    Optional<Formacao> findByIdAndCandidatoId(Long id, Long candidatoId);
}