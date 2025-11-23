package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.AtualizarUsuarioDto;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioGerenciamentoService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário logado não encontrado."));
    }

    @Transactional
    public Usuario buscarPerfilCompletoPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        // Força a inicialização das coleções lazy dentro da transação para o DTO
        usuario.getUsuarioSkills().size();
        usuario.getFormacoes().size();
        usuario.getCandidaturas().forEach(c -> c.getStatusHistorico().size());

        return usuario;
    }

    @Transactional
    public Usuario atualizar(String email, AtualizarUsuarioDto dto) {
        Usuario usuario = buscarPorEmail(email);

        // 1. Atualiza os dados básicos do usuário
        if (dto.nome() != null && !dto.nome().isBlank()) {
            usuario.setNome(dto.nome());
        }

        if (dto.role() != null) {
            usuario.setRole(dto.role());
        }

        if (dto.genero() != null) {
            usuario.setGenero(dto.genero());
        }

        if (dto.telefone() != null && !dto.telefone().isBlank()) {
            usuario.setTelefone(dto.telefone());
        }

        // 2. Lógica de atualização de Skills
        if (dto.skills() != null) {
            // Remove todas as skills existentes do usuário
            usuarioService.removerSkills(usuario);

            // Se a lista de skills fornecida não for vazia, associa as novas
            if (!dto.skills().isEmpty()) {
                usuarioService.associarSkills(usuario, dto.skills());
            }
        }

        // 3. Salva e retorna o usuário atualizado
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletar(String email) {
        Usuario usuario = buscarPorEmail(email);

        usuario.setDeleted(1);
        usuarioRepository.save(usuario);
    }

    public Page<Usuario> listar(Optional<Integer> deletedStatus, Pageable pageable) {
        if (deletedStatus.isPresent() && (deletedStatus.get() == 0 || deletedStatus.get() == 1)) {
            return usuarioRepository.findByDeleted(deletedStatus.get(), pageable);
        }
        return usuarioRepository.findAll(pageable);
    }
}