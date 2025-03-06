package com.marcelo.ms.cursos.services;

import java.util.List;
import java.util.Optional;

import com.marcelo.ms.cursos.models.Usuario;
import com.marcelo.ms.cursos.models.entity.Curso;

public interface CursoService {
	
	List<Curso> findAll();
	
	Optional<Curso> findById(Long id);
	
	Curso save(Curso curso);
	
	void delete(Long id);
	
	Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
	
	Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
	
	Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
	
	Optional<Curso> porIdConUsuarios(Long id);
	
	void eliminarCursoUsuarioPorUsuarioId(Long usuarioId);
	
}
