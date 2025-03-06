package com.marcelo.usuarios.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.usuarios.models.entity.Usuario;
import com.marcelo.usuarios.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping
	public List<Usuario> listar() {
		return usuarioService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarId(@PathVariable Long id) {
		Optional<Usuario> usuarioOptional = usuarioService.findById(id);
		
		if(usuarioOptional.isPresent()) {
//			return ResponseEntity.ok().body(usuarioOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
		}
		
		return ResponseEntity.notFound().build();
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario, BindingResult result) {
		
		if(result.hasErrors()) {
			return validar(result);
		}
		
		Optional<Usuario> optional = usuarioService.findByEmail(usuario.getEmail());
		if(!usuario.getEmail().isEmpty() && optional.isPresent()) {
			return ResponseEntity.badRequest().body(Collections
					.singletonMap("Email", "El correo "+ usuario.getEmail() + " ya existe en la bd"));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result ,@PathVariable Long id) {
		if(result.hasErrors()) {
			validar(result);
		}
		Optional<Usuario> usuarioOptional = usuarioService.findById(id);
		if(usuarioOptional.isPresent()) { // si lo encuentra y ademas ese correo no existe en la bd
			Usuario usuarioDb = usuarioOptional.get();
			if(!usuarioDb.getEmail().equalsIgnoreCase(usuario.getEmail()) && usuarioService.findByEmail(usuario.getEmail()).isPresent()) {
				return ResponseEntity.badRequest().body(Collections
						.singletonMap("Email", "El correo "+ usuario.getEmail() + " ya existe en la bd"));
			}
			usuarioDb.setNombre(usuario.getNombre());
			usuarioDb.setEmail(usuario.getEmail());
			usuarioDb.setPassword(usuario.getPassword());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioDb));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Usuario> usuarioOptional = usuarioService.findById(id);
		
		if(usuarioOptional.isPresent()) {
			usuarioService.delete(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/usuarios-curso")
	public ResponseEntity<?> obtenerUsuariosPorCurso(@RequestParam List<Long> ids) {
		return ResponseEntity.ok(usuarioService.listarPorIds(ids));
	}
	
	private ResponseEntity<Map<String,String>> validar(BindingResult result){
		Map<String,String> errores = new HashMap<>();
		result.getFieldErrors().forEach(error -> {
			errores.put(error.getField(), "El campo "+error.getField() + " " + error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
}
