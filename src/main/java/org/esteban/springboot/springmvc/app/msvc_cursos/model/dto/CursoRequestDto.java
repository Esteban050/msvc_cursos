package org.esteban.springboot.springmvc.app.msvc_cursos.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequestDto {

    @NotBlank(message = "El nombre del curso es obligatorio")
    private String nombre;
}
