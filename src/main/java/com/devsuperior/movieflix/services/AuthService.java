package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public User authenticated() throws Exception {
		try { 
		//pega o usuário já autenticado pelo Spring Security
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(username);
		} 
		catch (Exception e) {
			throw new Exception();
		}
	}
	
	public void validateVisitorOrMember(Long userId) throws Exception {
		User user = authenticated();
		if(!user.getId().equals(userId) && !user.hasRole("ROLE_MEMBER")) {
			throw new Exception("not member");
		}
	}
	
//	public void validateSelfOrMember(Long userId) throws Exception {
//		User user = authenticated();
//		if (!user.getId().equals(userId) && !user.hasRole("ROLE_MEMBER")) {
//			throw
//		}
//	}
	
}
