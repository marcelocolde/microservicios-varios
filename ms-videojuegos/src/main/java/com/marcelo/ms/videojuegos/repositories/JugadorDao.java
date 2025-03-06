package com.marcelo.ms.videojuegos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.marcelo.ms.videojuegos.models.entities.Jugador;

public interface JugadorDao extends CrudRepository<Jugador, Long>{

}
