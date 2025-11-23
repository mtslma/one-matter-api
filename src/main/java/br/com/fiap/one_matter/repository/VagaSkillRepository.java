package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.VagaSkill;
import br.com.fiap.one_matter.model.VagaSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaSkillRepository extends JpaRepository<VagaSkill, VagaSkillId> {
}