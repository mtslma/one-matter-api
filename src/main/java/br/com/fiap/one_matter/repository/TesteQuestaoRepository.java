package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.TesteQuestao;
import br.com.fiap.one_matter.model.TesteQuestaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteQuestaoRepository extends JpaRepository<TesteQuestao, TesteQuestaoId> {
}