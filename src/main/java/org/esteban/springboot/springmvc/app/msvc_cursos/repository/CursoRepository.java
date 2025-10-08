package org.esteban.springboot.springmvc.app.msvc_cursos.repository;

import org.esteban.springboot.springmvc.app.msvc_cursos.model.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
