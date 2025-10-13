package org.esteban.springboot.springmvc.app.msvc_cursos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esteban.springboot.springmvc.app.msvc_cursos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponseDto {

    private Long id;
    private String nombre;

    @Builder.Default
    private List<Usuario> usuarios = new ArrayList<>();
}
