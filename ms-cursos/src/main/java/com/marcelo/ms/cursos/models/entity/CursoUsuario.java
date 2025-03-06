package com.marcelo.ms.cursos.models.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos_usuarios")
public class CursoUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "usuario_id", unique = true)// id usuario unico para un curso en particular
	private Long usuarioId;
	
	// otra columna mas que se genera automaticamente con una relacion oneToMany para los curso_id
	// al hacer un save de curso se graba aca el su cursoId por tener un atributo joincolumn referenciando a esta tabla

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuarioId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CursoUsuario other = (CursoUsuario) obj;
		return Objects.equals(usuarioId, other.usuarioId);
	}
	
	
	
	
	
	
	
	
}
