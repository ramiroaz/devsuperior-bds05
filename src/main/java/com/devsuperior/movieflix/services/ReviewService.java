package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository repository;

  @Autowired
  private AuthService authService;

  @Transactional(readOnly = true)
  public ReviewDTO findById(Long id) {
    //authService.validateSelfOrMember(id);
    Optional<Review> obj = repository.findById(id);
    Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new ReviewDTO(entity);
  }
}