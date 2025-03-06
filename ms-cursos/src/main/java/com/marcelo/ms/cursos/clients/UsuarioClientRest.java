package com.marcelo.ms.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcelo.ms.cursos.models.Usuario;

@FeignClient(name = "ms-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

	@GetMapping("/{id}")
	Usuario detalle(@PathVariable Long id);
	
	@PostMapping
	Usuario save(@RequestBody Usuario usuario);
	
	@GetMapping("/usuarios-curso")
	List<Usuario> obtenerUsuariosPorCurso(@RequestParam List<Long> ids);
	
}
