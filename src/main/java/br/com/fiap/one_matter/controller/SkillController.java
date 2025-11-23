package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.AssociacaoRequestDto;
import br.com.fiap.one_matter.dto.request.SkillRequestDto;
import br.com.fiap.one_matter.dto.response.SkillResponseDto;
import br.com.fiap.one_matter.model.Skill;
import br.com.fiap.one_matter.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
// REMOVIDO: @PreAuthorize("hasRole('ADMIN')") da classe para liberar o GET
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<Page<SkillResponseDto>> listarSkills(
            @RequestParam(required = false) Optional<Integer> deleted,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Skill> page = skillService.listar(deleted, pageable);
        return ResponseEntity.ok(page.map(SkillResponseDto::fromSkill));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDto> buscarSkillPorId(@PathVariable Long id) {
        Skill s = skillService.buscarPorIdAtiva(id);
        return ResponseEntity.ok(SkillResponseDto.fromSkill(s));
    }

    // Métodos de escrita continuam restritos ao ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillResponseDto> criarSkill(@RequestBody @Valid SkillRequestDto dto) {
        Skill s = skillService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SkillResponseDto.fromSkill(s));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillResponseDto> atualizarSkill(
            @PathVariable Long id,
            @RequestBody @Valid SkillRequestDto dto) {
        Skill s = skillService.atualizar(id, dto);
        return ResponseEntity.ok(SkillResponseDto.fromSkill(s));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarSkill(@PathVariable Long id) {
        skillService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/associar-vaga")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> associarSkillVaga(@RequestBody @Valid AssociacaoRequestDto dto) {
        skillService.associarVaga(dto.idPrincipal(), dto.idAssociado());
        return ResponseEntity.ok("Skill " + dto.idPrincipal() + " associada à Vaga " + dto.idAssociado());
    }
}