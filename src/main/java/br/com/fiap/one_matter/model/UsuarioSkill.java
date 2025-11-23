package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
// CORREÇÃO: Nome da tabela ajustado para bater com o seu data.sql
@Table(name = "TB_ONEM_CANDIDATO_SKILL")
public class UsuarioSkill {

    @EmbeddedId
    private UsuarioSkillId id;

    @ManyToOne
    // CORREÇÃO: id_candidato deve bater com o nome da coluna no banco
    @MapsId("candidatoId")
    @JoinColumn(name = "id_candidato")
    private Usuario candidato;

    @ManyToOne
    // CORREÇÃO: id_skill deve bater com o nome da coluna no banco
    @MapsId("skillId")
    @JoinColumn(name = "id_skill")
    private Skill skill;

    @Column(name = "ds_nivel")
    private String nivel; // EX: INICIANTE, INTERMEDIARIO, AVANCADO
}