package org.esteban.springboot.springmvc.app.msvc_cursos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String password;
}
