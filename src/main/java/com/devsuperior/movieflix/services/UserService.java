package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthService authService;
	
	public List<UserDTO> findAll() {
		List<User> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
//	@Transactional(readOnly = true)
//	public UserDTO findById(Long id) {
//		Optional<User> obj = repository.findById(id);
//		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
//		return new UserDTO(entity);
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// retorna os dados do user, dado um e-mail (email no caso é username
		User user = repository.findByEmail(username);
		if (user == null ) {
			logger.error("Usuário não encontrado :" + username);
			throw new UsernameNotFoundException("E-mail não encontrado");
		}
		logger.info("Usuário encontrado : " + username);
		return user;
	}
	
	@Transactional(readOnly = true)
	public UserDTO getProfile() {
		try {
			return new UserDTO(authService.authenticated());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
