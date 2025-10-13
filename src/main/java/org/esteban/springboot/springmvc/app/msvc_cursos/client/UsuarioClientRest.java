package org.esteban.springboot.springmvc.app.msvc_cursos.client;

import org.esteban.springboot.springmvc.app.msvc_cursos.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
