package org.esteban.springboot.springmvc.app.msvc_cursos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoRequestDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.dto.CursoResponseDto;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.entities.Curso;
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
