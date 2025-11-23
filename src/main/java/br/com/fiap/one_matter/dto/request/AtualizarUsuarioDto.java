package br.com.fiap.one_matter.dto.request;

import br.com.fiap.one_matter.enums.Genero;
import br.com.fiap.one_matter.enums.UsuarioRole;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AtualizarUsuarioDto(
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        UsuarioRole role,

        // CORRIGIDO: Removida a anotação @Size, pois Genero é um Enum
        Genero genero,

        @Size(max = 13, message = "O telefone deve ter no máximo 13 caracteres.")
        String telefone,

        List<Long> skills
) {}