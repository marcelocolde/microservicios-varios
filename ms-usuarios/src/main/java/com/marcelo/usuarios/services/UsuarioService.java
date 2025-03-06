package com.marcelo.usuarios.services;

import java.util.List;
import java.util.Optional;

import com.marcelo.usuarios.models.entity.Usuario;

public interface UsuarioService {
	
	List<Usuario> findAll();
	
	Optional<Usuario> findById(Long id);
	
	Usuario save(Usuario usuario);
	
	void delete(Long id);
	
	Optional<Usuario> findByEmail(String email);
	
	List<Usuario> listarPorIds(Iterable<Long> ids);
	
}
