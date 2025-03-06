package com.marcelo.ms.cursos.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.marcelo.ms.cursos.models.entity.Curso;

public interface CursoDao extends CrudRepository<Curso, Long>{
	
	@Modifying
	@Query("delete from CursoUsuario cu where cu.usuarioId = ?1")
	void eliminarCursoUsuarioPorUsuarioId(Long id);
	
}
