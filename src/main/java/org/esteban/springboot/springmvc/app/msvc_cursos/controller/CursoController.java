package org.esteban.springboot.springmvc.app.msvc_cursos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.Usuario;
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

    @GetMapping("/{id}/con-usuarios")
    public ResponseEntity<CursoResponseDto> findByIdConUsuarios(@PathVariable Long id) {
        return cursoService.findByIdConUsuarios(id)
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

    @PutMapping("/{cursoId}/asignar-usuario/{usuarioId}")
    public ResponseEntity<Usuario> asignarUsuario(
            @PathVariable Long cursoId,
            @PathVariable Long usuarioId) {
        return cursoService.asignarUsuario(cursoId, usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{cursoId}/crear-usuario")
    public ResponseEntity<Usuario> crearUsuario(
            @PathVariable Long cursoId,
            @Valid @RequestBody Usuario usuario) {
        return cursoService.crearUsuario(cursoId, usuario)
                .map(usuario1 -> ResponseEntity.status(HttpStatus.CREATED).body(usuario1))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cursoId}/desasignar-usuario/{usuarioId}")
    public ResponseEntity<Usuario> desasignarUsuario(
            @PathVariable Long cursoId,
            @PathVariable Long usuarioId) {
        return cursoService.desasignarUsuario(cursoId, usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar-usuario/{usuarioId}")
    public ResponseEntity<Void> eliminarCursoUsuarioPorId(@PathVariable Long usuarioId) {
        cursoService.eliminarCursoUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
