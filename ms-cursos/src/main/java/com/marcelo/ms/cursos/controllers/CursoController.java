package com.marcelo.ms.cursos.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.ms.cursos.models.Usuario;
import com.marcelo.ms.cursos.models.entity.Curso;
import com.marcelo.ms.cursos.services.CursoService;

import feign.FeignException;
import jakarta.validation.Valid;

@RestController
public class CursoController {

	@Autowired
	private CursoService service;
	
	@GetMapping
	public ResponseEntity<List<Curso>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarId(@PathVariable Long id){
		Optional<Curso> optional = service.porIdConUsuarios(id); // service.findById(id);
		Map<String,Object> map = new HashMap<>();
		if(optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optional.get());
		}
		
		map.put("error", "no se encontro ningun curso con el ID:"+id);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Curso curso, BindingResult result){
		
		if(result.hasErrors()) {
			return validar(result);
		}
		
		Curso cursoDb = service.save(curso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);
		if(optional.isPresent()) {
			Curso cursoDb = optional.get();
			cursoDb.setNombre(curso.getNombre());
			service.save(cursoDb);
			return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
		}		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);
		if(optional.isPresent()) {
			service.delete(id);
			// TODO aca se deberia eliminar tambien de CursoUsuario, aunque esta configurado en cascada, revisar
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/asignar-usuario/{cursoId}")
	public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		Optional<Usuario> o;
		try {
			o = service.asignarUsuario(usuario, cursoId);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "no existe el usuario por " +
						" el id o hubo un error en la comunicación: " + e.getMessage()) );
		}
		
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/crear-usuario/{cursoId}")
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		Optional<Usuario> o;
		try {
			o = service.crearUsuario(usuario, cursoId);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "no se pudo crear el usuario "
							+ "o hubo un error en la comunicación: " 
							+ e.getMessage()) );
		}
		
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
		}
		
		return ResponseEntity.notFound().build();
	}
		
	@DeleteMapping("/desasignar-usuario/{cursoId}")
	public ResponseEntity<?> desasignarUsuario(@RequestBody Usuario usuario,@PathVariable Long cursoId) {
		Optional<Usuario> o;
		try {
			o = service.eliminarUsuario(usuario, cursoId);
			
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "no se pudo eliminar el usuario "
							+ "o hubo un error en la comunicación: " 
							+ e.getMessage()) );
		}
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/eliminar-curso-usuario/{usuarioId}")
	public ResponseEntity<?> eliminarCursoUsuarioPorUsuarioId(@PathVariable Long usuarioId) {
		service.eliminarCursoUsuarioPorUsuarioId(usuarioId);
		return ResponseEntity.noContent().build();
	}
	
	
	private ResponseEntity<?> validar(BindingResult result) {
		Map<String,String> errores = new HashMap<>();
		result.getFieldErrors().forEach(error -> {
			errores.put(error.getField(), "El campo "+error.getField() + " " + error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	
	
	
	
}
