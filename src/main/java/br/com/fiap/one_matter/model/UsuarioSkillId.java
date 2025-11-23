package br.com.fiap.one_matter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSkillId implements Serializable {

    // CORREÇÃO: Nome da coluna ajustado para o padrão do banco legado/data.sql
    @Column(name = "id_candidato")
    private Long candidatoId;

    @Column(name = "id_skill")
    private Long skillId;
}