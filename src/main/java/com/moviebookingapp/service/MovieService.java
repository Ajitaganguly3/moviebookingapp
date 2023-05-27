package com.moviebookingapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.dto.MoviesDTO;
import com.moviebookingapp.model.Movies;
import com.moviebookingapp.repository.MoviesRepository;

@Service
public class MovieService {

	private MoviesRepository moviesRepository;

	@Autowired
	public MovieService(MoviesRepository moviesRepository) {
		this.moviesRepository = moviesRepository;
	}

	public List<MoviesDTO> getAllMovies() {

		List<Movies> movies = moviesRepository.findAllByOrderByMovienameAsc();
		return convertToDTOList(movies);

	}
	
	public List<MoviesDTO> searchMovies(String moviename){
		List<Movies> movies = moviesRepository.findByMovienameContainingIgnoreCaseOrderByMovienameAsc(moviename);
		return convertToDTOList(movies);
	}
	
	private List<MoviesDTO> convertToDTOList(List<Movies> movies){
		return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private MoviesDTO convertToDTO(Movies movie) {
		
		MoviesDTO moviesDto = new MoviesDTO();
		moviesDto.setMoviename(movie.getMoviename());
		moviesDto.setTheatrename(movie.getTheatrename());
		moviesDto.setNoOfTicketsAllotted(movie.getNoOfTicketsAllotted());
		moviesDto.setStatus(movie.getStatus());
		
		return moviesDto;
		
	}

}
