package com.marcelo.ms.cursos.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.ms.cursos.clients.UsuarioClientRest;
import com.marcelo.ms.cursos.models.Usuario;
import com.marcelo.ms.cursos.models.entity.Curso;
import com.marcelo.ms.cursos.models.entity.CursoUsuario;
import com.marcelo.ms.cursos.repositories.CursoDao;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	private CursoDao dao;
	
	@Autowired
	private UsuarioClientRest client;
	
	@Override
	@Transactional(readOnly = true)
	public List<Curso> findAll() {
		return (List<Curso>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Curso save(Curso curso) {
		return dao.save(curso);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);		
	}

	@Override
	@Transactional
	public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
		Optional<Curso> optionalCurso = dao.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Usuario usuarioMs = client.detalle(usuario.getId());
			Curso curso = optionalCurso.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			
			cursoUsuario.setUsuarioId(usuarioMs.getId());
			curso.addCursoUsuario(cursoUsuario);
			dao.save(curso); // en realidad es como un update, se genera un nuevo registro a la tabla intermedia cursos_usuarios
			return Optional.of(usuarioMs);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
		Optional<Curso> optionalCurso = dao.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Curso curso = optionalCurso.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			
			Usuario usuarioMs = client.save(usuario);
			cursoUsuario.setUsuarioId(usuarioMs.getId());
			curso.addCursoUsuario(cursoUsuario);
			
			dao.save(curso);
			
			return Optional.of(usuarioMs);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
		Optional<Curso> optionalCurso = dao.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Usuario usuarioMs = client.detalle(usuario.getId());
			Curso curso = optionalCurso.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			
			cursoUsuario.setUsuarioId(usuarioMs.getId());
			curso.removeCursoUsuario(cursoUsuario);
			dao.save(curso); // esto desasigna un usuario de un curso
			return Optional.of(usuarioMs);
		}
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> porIdConUsuarios(Long id) {
		Optional<Curso> o = dao.findById(id);
		if(o.isPresent()) {
			Curso curso = o.get();
			if(!curso.getCursoUsuarios().isEmpty()) {
				// si no esta vacio obtengo las lista de ids de este curso
				List<Long> ids = curso.getCursoUsuarios().stream().map(cu -> cu.getUsuarioId()).toList();
				List<Usuario> usuarios = client.obtenerUsuariosPorCurso(ids);
				curso.setUsuarios(usuarios);
			}
			return Optional.of(curso);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public void eliminarCursoUsuarioPorUsuarioId(Long usuarioId) {
		dao.eliminarCursoUsuarioPorUsuarioId(usuarioId);
	}

}
