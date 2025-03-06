package com.marcelo.ms.videojuegos.services;

import java.util.List;

import com.marcelo.ms.videojuegos.models.entities.Jugador;

public interface JugadorService {
	
	List<Jugador> findAll();
	
	Jugador save(Jugador jugador);

}
