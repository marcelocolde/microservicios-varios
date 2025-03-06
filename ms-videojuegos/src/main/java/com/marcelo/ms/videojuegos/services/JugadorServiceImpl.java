package com.marcelo.ms.videojuegos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.ms.videojuegos.models.entities.Jugador;
import com.marcelo.ms.videojuegos.repositories.JugadorDao;

@Service
public class JugadorServiceImpl implements JugadorService{

	@Autowired
	private JugadorDao jugadorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Jugador> findAll() {
		return (List<Jugador>) jugadorDao.findAll();
	}

	@Override
	@Transactional
	public Jugador save(Jugador jugador) {
		return jugadorDao.save(jugador);
	}

}
