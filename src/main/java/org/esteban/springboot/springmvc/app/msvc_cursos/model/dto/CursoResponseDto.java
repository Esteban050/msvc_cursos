package org.esteban.springboot.springmvc.app.msvc_cursos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponseDto {

    private Long id;
    private String nombre;
}
