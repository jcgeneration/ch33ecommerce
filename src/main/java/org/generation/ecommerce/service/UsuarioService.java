package org.generation.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.generation.ecommerce.model.ChangePassword;
import org.generation.ecommerce.model.Usuario;
import org.generation.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}//constructor
	
	public List<Usuario> getAllUsers(){
			return usuarioRepository.findAll();
	}//getAllUsers
	
	public Usuario getUser(Long id){
		return usuarioRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("El Usuario con el id [" + id
						+ "] no existe")
			  );
}//getUser

	public Usuario deleteUser (Long id){
		Usuario user = null;
		if (usuarioRepository.existsById(id)) {
			user = usuarioRepository.findById(id).get();
			usuarioRepository.deleteById(id);
		}//if existById
		return user;
	}//deleteUser
	
	public Usuario addUser(Usuario usuario) {
		Usuario user = null;
		if  (usuarioRepository.findByEmail(usuario.getEmail()).isEmpty())  {
			//Cifrar la contraseña
			usuario.setPassword( passwordEncoder.encode(usuario.getPassword()) );
			user = usuarioRepository.save(usuario);
		} else{
			System.out.println("El usuario con el email [" + usuario.getEmail()	+ "] ya se encuentra registrado");
		}//if isEmpty
		return user;
	}//addUser
	public Usuario updateUser(Long id, ChangePassword changePassword) {
		Usuario user = null;
		if (usuarioRepository.existsById(id)) {
			user = usuarioRepository.findById(id).get();
			if(passwordEncoder.matches(changePassword.getPassword(),user.getPassword())) {
			//if (user.getPassword().equals(changePassword.getPassword())) {
				user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
				usuarioRepository.save(user);
			}else {
				System.out.println("updateUser - El password es incorrecto id[" + id
						+ "]");
				user=null;
			}//if equals
		} else {
			System.out.println("updateUser - El usuario con el id[" + id
					+ "] NO se encuentra registrado");
		}//else //if existById
		return user;
	}//updateUser

	public boolean validateUser(Usuario usuario) {
		Optional<Usuario> userByEmail = usuarioRepository.findByEmail(usuario.getEmail()); 
		if (userByEmail.isPresent()) {
			Usuario user = userByEmail.get();
			if(passwordEncoder.matches(usuario.getPassword(),user.getPassword())) {
				return true;
			}//if password equals
		}//if isPresent
		return false;
	}//validateUser
	
	
	
}//class UsuarioService




