package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailAndDeleted(String email, Integer deleted);

    Optional<Usuario> findByCpfAndDeleted(String cpf, Integer deleted);

    boolean existsByCpf(String cpf);

    default Optional<Usuario> findByEmail(String email) {
        return findByEmailAndDeleted(email, 0);
    }

    Page<Usuario> findByDeleted(Integer deleted, Pageable pageable);
}