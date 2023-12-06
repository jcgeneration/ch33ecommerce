package org.generation.ecommerce.controller;

import java.util.List;

import org.generation.ecommerce.model.ChangePassword;
import org.generation.ecommerce.model.Usuario;
import org.generation.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/usuarios/")
public class UsuarioController {

	private final UsuarioService usuarioService;
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}//constructor
	@GetMapping
	public List<Usuario> getAllUsuarios(){
		return usuarioService.getAllUsers();
	}//getAllUsuarios
	
	@GetMapping(path="{userId}")
	public Usuario getUsuario(@PathVariable("userId") Long id){
		return usuarioService.getUser(id);
	}//getUsuario
	
	@DeleteMapping(path="{userId}")
	public Usuario deleteUsuario(@PathVariable("userId") Long id){
		return usuarioService.deleteUser(id);
	}//deleteUsuario
	
	@PostMapping
	public Usuario addUsuario(@RequestBody Usuario usuario) {
		return usuarioService.addUser(usuario);
	}//addUsuario
	
	@PutMapping (path="{userId}")
	public Usuario updateUser(@PathVariable("userId") Long id, 
			@RequestBody ChangePassword changePassword) {	
	return usuarioService.updateUser(id, changePassword);
	}//updateUsuario
	
}//class UsuarioController 
