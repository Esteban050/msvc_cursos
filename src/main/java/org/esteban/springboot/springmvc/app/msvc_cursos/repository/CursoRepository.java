package org.esteban.springboot.springmvc.app.msvc_cursos.repository;

import org.esteban.springboot.springmvc.app.msvc_cursos.model.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Modifying
    @Query("DELETE FROM CursoUsuario cu WHERE cu.usuarioId = ?1")
    void eliminarCursoUsuarioPorId(Long usuarioId);
}
