package br.com.fiap.one_matter.dto.request;

import br.com.fiap.one_matter.enums.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

public record CadastroUsuarioDto(
        @NotBlank(message = "O nome não pode ser vazio.")
        @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail não pode ser vazio.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "A senha não pode ser vazia.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,

        @NotBlank(message = "O CPF não pode ser vazio.")
        @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória.")
        Instant dataNascimento,

        @NotNull(message = "O gênero é obrigatório.")
        Genero genero,

        @Size(max = 13, message = "O telefone deve ter no máximo 13 caracteres.")
        String telefone,

        // Novo campo: Lista de IDs das skills selecionadas
        List<Long> skills
) {}