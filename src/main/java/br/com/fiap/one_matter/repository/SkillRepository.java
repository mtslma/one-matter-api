package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Page<Skill> findByDeleted(Integer deleted, Pageable pageable);
    Optional<Skill> findByIdAndDeleted(Long id, Integer deleted);
}