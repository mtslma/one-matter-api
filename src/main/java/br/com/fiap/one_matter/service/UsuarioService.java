package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.CadastroUsuarioDto;
import br.com.fiap.one_matter.dto.request.CadastroUsuarioGerenciadoDto;
import br.com.fiap.one_matter.enums.UsuarioRole;
import br.com.fiap.one_matter.model.Skill;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.model.UsuarioSkill;
import br.com.fiap.one_matter.model.UsuarioSkillId;
import br.com.fiap.one_matter.repository.SkillRepository;
import br.com.fiap.one_matter.repository.UsuarioRepository;
import br.com.fiap.one_matter.repository.UsuarioSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UsuarioSkillRepository usuarioSkillRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Cria um usuário padrão (Candidato) via endpoint público, com associação de Skills.
     */
    @Transactional
    public Usuario criarDefaultUser(CadastroUsuarioDto dados) {
        // 1. Validação
        validarDuplicidade(dados.email(), dados.cpf());

        // 2. Criação do objeto
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dados.nome());
        novoUsuario.setEmail(dados.email());
        novoUsuario.setSenhaHash(passwordEncoder.encode(dados.senha()));
        novoUsuario.setCpf(dados.cpf());
        novoUsuario.setDataNascimento(dados.dataNascimento());
        novoUsuario.setGenero(dados.genero());
        novoUsuario.setTelefone(dados.telefone());
        novoUsuario.setRole(UsuarioRole.USER); // Sempre USER
        novoUsuario.setDataCriacao(Instant.now());
        novoUsuario.setDeleted(0);

        // 3. Salva usuário
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 4. Associa Skills (se houver)
        if (dados.skills() != null && !dados.skills().isEmpty()) {
            associarSkills(usuarioSalvo, dados.skills());
        }

        return usuarioSalvo;
    }

    /**
     * Cria um usuário gerenciado (Admin/Recrutador) via painel administrativo.
     */
    @Transactional
    public Usuario criarGerenciado(CadastroUsuarioGerenciadoDto dto) {
        // 1. Validação
        validarDuplicidade(dto.email(), dto.cpf());

        // 2. Criação do objeto
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenhaHash(passwordEncoder.encode(dto.senha()));
        novoUsuario.setCpf(dto.cpf());
        novoUsuario.setDataNascimento(dto.dataNascimento());
        novoUsuario.setGenero(dto.genero());
        novoUsuario.setTelefone(dto.telefone());
        novoUsuario.setRole(dto.role()); // Role definida pelo DTO
        novoUsuario.setDataCriacao(Instant.now());
        novoUsuario.setDeleted(0);

        // 3. Salva usuário
        return usuarioRepository.save(novoUsuario);
    }

    // Método auxiliar para validação
    private void validarDuplicidade(String email, String cpf) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Já existe um usuário com este e-mail.");
        }
        if (usuarioRepository.existsByCpf(cpf)) {
            throw new RuntimeException("Já existe um usuário com este CPF.");
        }
    }

    // Método auxiliar para salvar as skills
    private void associarSkills(Usuario usuario, List<Long> skillIds) {
        List<Skill> skillsEncontradas = skillRepository.findAllById(skillIds);

        for (Skill skill : skillsEncontradas) {
            // Cria a chave composta
            UsuarioSkillId id = new UsuarioSkillId(usuario.getId(), skill.getId());

            // Cria a entidade de relacionamento
            UsuarioSkill usuarioSkill = new UsuarioSkill();
            usuarioSkill.setId(id);
            usuarioSkill.setCandidato(usuario); // Nome do campo na entidade UsuarioSkill
            usuarioSkill.setSkill(skill);
            usuarioSkill.setNivel("INICIANTE"); // Valor padrão

            usuarioSkillRepository.save(usuarioSkill);
        }
    }
}