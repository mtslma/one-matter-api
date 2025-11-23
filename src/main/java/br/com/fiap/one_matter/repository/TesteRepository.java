package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Teste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends JpaRepository<Teste, Long> {
}