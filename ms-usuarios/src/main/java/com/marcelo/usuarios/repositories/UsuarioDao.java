package com.marcelo.usuarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.marcelo.usuarios.models.entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {
	// validar por email mediante nombres de metodos
	
	Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email); // otra alternativa
	
	@Query("select u FROM Usuario u WHERE u.email=?1")
	Optional<Usuario> porEmail(String email); // otra alternativa con query personalizada utilizando jpql
	
	
	Optional<Usuario> findByNombre(String nombre);
	
	
	
}
