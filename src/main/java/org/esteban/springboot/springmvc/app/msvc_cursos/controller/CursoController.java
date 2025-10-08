package org.esteban.springboot.springmvc.app.msvc_cursos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoRequestDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoResponseDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponseDto>> findAll() {
        List<CursoResponseDto> cursos = cursoService.findAll();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDto> findById(@PathVariable Long id) {
        return cursoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoResponseDto> create(@Valid @RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto savedCurso = cursoService.save(cursoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequestDto cursoRequestDto) {
        return cursoService.update(id, cursoRequestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = cursoService.deleteById(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
