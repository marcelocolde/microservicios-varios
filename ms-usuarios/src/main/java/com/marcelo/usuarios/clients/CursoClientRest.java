package com.marcelo.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cursos", url = "host.docker.internal:8002")
public interface CursoClientRest {
	
	@DeleteMapping("/eliminar-curso-usuario/{usuarioId}")
	void eliminarCursoUsuarioPorUsuarioId(@PathVariable Long usuarioId);
}
