package com.devsuperior.movieflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> list = service.findAll();		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id){
		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}	
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<UserDTO> findByName(@ PathVariable String username) {
		UserDTO dto = (UserDTO) service.loadUserByUsername(username);
		return ResponseEntity.ok().body(dto);	
	}
	
	@GetMapping(value = "/profile2")
	public ResponseEntity<UserDTO> getProfile2() {
		UserDTO dto = service.getProfile();
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/profile")
	public ResponseEntity<UserDTO> getProfile(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDTO dto = service.findByEmail(authentication.getPrincipal().toString());
		return ResponseEntity.ok().body(dto);
	}	
	
}
