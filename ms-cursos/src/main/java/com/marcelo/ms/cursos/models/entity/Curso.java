package com.marcelo.ms.cursos.models.entity;

import java.util.ArrayList;
import java.util.List;

import com.marcelo.ms.cursos.models.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cursos")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "curso_id")
	private List<CursoUsuario> cursoUsuarios;
	
	@Transient
	private List<Usuario> usuarios;
	
	public Curso() {
		cursoUsuarios = new ArrayList<>();
		usuarios = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CursoUsuario> getCursoUsuarios() {
		return cursoUsuarios;
	}

	public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
		this.cursoUsuarios = cursoUsuarios;
	}
	
	public void addCursoUsuario(CursoUsuario cursoUsuario) {
		cursoUsuarios.add(cursoUsuario);
	}
	
	public void removeCursoUsuario(CursoUsuario cursoUsuario) {
		cursoUsuarios.remove(cursoUsuario); // sobreescribir el metodo equals, para cambiar la config que en lugar de comparar por INSTANCIA compare por ID
		// el remove recorre la lista uno por uno , pero como lo hace por instancia siempre sera distinto
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
		
	
}
