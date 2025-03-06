package com.marcelo.ms.videojuegos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.ms.videojuegos.models.entities.Jugador;
import com.marcelo.ms.videojuegos.services.JugadorService;

@RestController
public class JugadorController {
	
	@Autowired
	private JugadorService jugadorService;
	
	@GetMapping
	public List<Jugador> showAll() {
		return jugadorService.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Jugador> save(@RequestBody Jugador jugador) {
		Jugador jugadorNew= jugadorService.save(jugador);
		jugadorService.save(jugadorNew);
		return ResponseEntity.status(HttpStatus.CREATED).body(jugadorNew);
	}
	
}
