package org.esteban.springboot.springmvc.app.msvc_cursos.service;

import org.esteban.springboot.springmvc.app.msvc_cursos.model.Usuario;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoRequestDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoResponseDto;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<CursoResponseDto> findAll();

    Optional<CursoResponseDto> findById(Long id);

    Optional<CursoResponseDto> findByIdConUsuarios(Long id);

    CursoResponseDto save(CursoRequestDto cursoRequestDto);

    Optional<CursoResponseDto> update(Long id, CursoRequestDto cursoRequestDto);

    boolean deleteById(Long id);

    Optional<Usuario> asignarUsuario(Long cursoId, Long usuarioId);

    Optional<Usuario> crearUsuario(Long cursoId, Usuario usuario);

    Optional<Usuario> desasignarUsuario(Long cursoId, Long usuarioId);

    void eliminarCursoUsuarioPorId(Long usuarioId);
}
