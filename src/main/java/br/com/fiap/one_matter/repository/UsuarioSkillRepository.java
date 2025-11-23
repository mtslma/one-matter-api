package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.model.UsuarioSkill;
import br.com.fiap.one_matter.model.UsuarioSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioSkillRepository extends JpaRepository<UsuarioSkill, UsuarioSkillId> {

    // NOVO: Método para deletar todas as skills de um usuário
    void deleteAllByCandidato(Usuario candidato);
}