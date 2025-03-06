package com.marcelo.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.usuarios.clients.CursoClientRest;
import com.marcelo.usuarios.models.entity.Usuario;
import com.marcelo.usuarios.repositories.UsuarioDao;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private CursoClientRest client;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long id) {
		return usuarioDao.findById(id);
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		usuarioDao.deleteById(id);
		client.eliminarCursoUsuarioPorUsuarioId(id);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioDao.findByEmail(email);
	}

	@Override
	public List<Usuario> listarPorIds(Iterable<Long> ids) {
		return (List<Usuario>) usuarioDao.findAllById(ids);
	}

}
