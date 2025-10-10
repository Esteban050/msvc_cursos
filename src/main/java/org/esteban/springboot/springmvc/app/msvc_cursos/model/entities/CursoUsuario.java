package org.esteban.springboot.springmvc.app.msvc_cursos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "cursos_usuarios")
@Getter
@Setter
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "usuario_id", unique = true)

    private long usuarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoUsuario that = (CursoUsuario) o;
        return usuarioId == that.usuarioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId);
    }
}
