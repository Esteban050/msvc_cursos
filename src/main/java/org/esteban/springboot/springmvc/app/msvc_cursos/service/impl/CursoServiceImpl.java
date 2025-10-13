package org.esteban.springboot.springmvc.app.msvc_cursos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.esteban.springboot.springmvc.app.msvc_cursos.client.UsuarioClientRest;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.Usuario;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoRequestDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoResponseDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.entities.Curso;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.entities.CursoUsuario;
import org.esteban.springboot.springmvc.app.msvc_cursos.repository.CursoRepository;
import org.esteban.springboot.springmvc.app.msvc_cursos.service.CursoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioClientRest usuarioClient;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDto> findAll() {
        log.info("Obteniendo todos los cursos");
        return cursoRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CursoResponseDto> findById(Long id) {
        log.info("Buscando curso con id: {}", id);
        return cursoRepository.findById(id)
                .map(this::mapToResponseDto);
    }

    @Override
    @Transactional
    public CursoResponseDto save(CursoRequestDto cursoRequestDto) {
        log.info("Creando nuevo curso con nombre: {}", cursoRequestDto.getNombre());
        Curso curso = mapToEntity(cursoRequestDto);
        Curso savedCurso = cursoRepository.save(curso);
        log.info("Curso creado exitosamente con id: {}", savedCurso.getId());
        return mapToResponseDto(savedCurso);
    }

    @Override
    @Transactional
    public Optional<CursoResponseDto> update(Long id, CursoRequestDto cursoRequestDto) {
        log.info("Actualizando curso con id: {}", id);
        return cursoRepository.findById(id)
                .map(existingCurso -> {
                    existingCurso.setNombre(cursoRequestDto.getNombre());
                    Curso updatedCurso = cursoRepository.save(existingCurso);
                    log.info("Curso actualizado exitosamente con id: {}", id);
                    return mapToResponseDto(updatedCurso);
                });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        log.info("Eliminando curso con id: {}", id);
        return cursoRepository.findById(id)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    log.info("Curso eliminado exitosamente con id: {}", id);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CursoResponseDto> findByIdConUsuarios(Long id) {
        log.info("Buscando curso con id: {} incluyendo usuarios", id);
        return cursoRepository.findById(id)
                .map(curso -> {
                    CursoResponseDto dto = mapToResponseDto(curso);
                    if (!curso.getCursoUsuarios().isEmpty()) {
                        List<Long> ids = curso.getCursoUsuarios().stream()
                                .map(CursoUsuario::getUsuarioId)
                                .collect(Collectors.toList());
                        List<Usuario> usuarios = usuarioClient.obtenerAlumnosPorCurso(ids);
                        dto.setUsuarios(usuarios);
                    }
                    return dto;
                });
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Long cursoId, Long usuarioId) {
        log.info("Asignando usuario {} al curso {}", usuarioId, cursoId);
        return cursoRepository.findById(cursoId)
                .map(curso -> {
                    Usuario usuario = usuarioClient.detalle(usuarioId);
                    CursoUsuario cursoUsuario = new CursoUsuario();
                    cursoUsuario.setUsuarioId(usuarioId);
                    curso.addCursoUsuario(cursoUsuario);
                    cursoRepository.save(curso);
                    log.info("Usuario asignado exitosamente al curso");
                    return usuario;
                });
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Long cursoId, Usuario usuario) {
        log.info("Creando usuario y asignándolo al curso {}", cursoId);
        return cursoRepository.findById(cursoId)
                .map(curso -> {
                    Usuario nuevoUsuario = usuarioClient.detalle(usuario.getId());
                    CursoUsuario cursoUsuario = new CursoUsuario();
                    cursoUsuario.setUsuarioId(nuevoUsuario.getId());
                    curso.addCursoUsuario(cursoUsuario);
                    cursoRepository.save(curso);
                    log.info("Usuario creado y asignado exitosamente al curso");
                    return nuevoUsuario;
                });
    }

    @Override
    @Transactional
    public Optional<Usuario> desasignarUsuario(Long cursoId, Long usuarioId) {
        log.info("Desasignando usuario {} del curso {}", usuarioId, cursoId);
        return cursoRepository.findById(cursoId)
                .map(curso -> {
                    Usuario usuario = usuarioClient.detalle(usuarioId);
                    curso.deleteCursoUsuarioPorId(usuarioId);
                    cursoRepository.save(curso);
                    log.info("Usuario desasignado exitosamente del curso");
                    return usuario;
                });
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long usuarioId) {
        log.info("Eliminando usuario {} de todos los cursos", usuarioId);
        cursoRepository.eliminarCursoUsuarioPorId(usuarioId);
        log.info("Usuario eliminado de todos los cursos exitosamente");
    }

    private CursoResponseDto mapToResponseDto(Curso curso) {
        return CursoResponseDto.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .build();
    }

    private Curso mapToEntity(CursoRequestDto cursoRequestDto) {
        Curso curso = new Curso();
        curso.setNombre(cursoRequestDto.getNombre());
        return curso;
    }
}
