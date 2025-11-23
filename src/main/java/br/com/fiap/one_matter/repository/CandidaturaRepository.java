package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.Candidatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {

    Page<Candidatura> findByDeleted(Integer deleted, Pageable pageable);
    Optional<Candidatura> findByIdAndDeleted(Long id, Integer deleted);
    Optional<Candidatura> findByCandidatoIdAndVagaIdAndDeleted(Long candidatoId, Long vagaId, Integer deleted);
    Page<Candidatura> findByCandidatoIdAndDeleted(Long candidatoId, Integer deleted, Pageable pageable);
    Page<Candidatura> findByVagaIdAndDeleted(Long vagaId, Integer deleted, Pageable pageable);

    @Procedure(procedureName = "SP_REGISTRAR_INICIO")
    void registrarInicioProva(Long p_id_candidatura);

    @Procedure(procedureName = "SP_FINALIZAR_PROVA")
    void finalizarProvaDB(Long p_id_candidatura, Double p_nota);

    @Query(value = "SELECT FN_VERIFICAR_CONCLUSAO(:id) FROM DUAL", nativeQuery = true)
    Integer verificarConclusao(@Param("id") Long idCandidatura);

    @Query(value = "SELECT FN_CALCULAR_SITUACAO(:nota) FROM DUAL", nativeQuery = true)
    String calcularSituacao(@Param("nota") Double nota);
}