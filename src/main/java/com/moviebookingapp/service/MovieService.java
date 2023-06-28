package com.moviebookingapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.MoviesDTO;
import com.moviebookingapp.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.exceptions.MovieNotFoundException;
import com.moviebookingapp.exceptions.TicketNotFoundException;
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

	public List<MoviesDTO> searchMovies(String moviename) {
		List<Movies> movies = moviesRepository.findByMovienameContainingIgnoreCaseOrderByMovienameAsc(moviename);
		return convertToDTOList(movies);
	}

	public MessageResponse addMovie(@Valid MoviesDTO moviesDTO) {

		Optional<Movies> movie = moviesRepository.findByMoviename(moviesDTO.getMoviename());
		if (movie.isPresent()) {
			return new MessageResponse("Movie already exists with this name", HttpStatus.NOT_FOUND);
		}

		Movies movies = new Movies();
		movies.setAbout(moviesDTO.getAbout());
		movies.setGenre(moviesDTO.getGenre());
		movies.setMoviename(moviesDTO.getMoviename());
		movies.setNoOfTicketsAllotted(moviesDTO.getNoOfTicketsAllotted());
		movies.setPosterURL(moviesDTO.getPosterURL());
		movies.setPrice(moviesDTO.getPrice());
		movies.setReleaseDate(moviesDTO.getReleaseDate());
		movies.setStatus(moviesDTO.getStatus());
		movies.setTheatrename(moviesDTO.getTheatrename());

		moviesRepository.save(movies);

		return new MessageResponse("Movies added successfully", HttpStatus.OK);
	}

	public MessageResponse deleteMovie(String moviename) throws MovieNotFoundException {

		Optional<Movies> movie = moviesRepository.findByMoviename(moviename);
		if (movie.isPresent()) {
			moviesRepository.delete(movie.get());
		} else {
			throw new MovieNotFoundException("Movie not found with the name: " + moviename);
		}
		return new MessageResponse("Movie Deleted Successfully", HttpStatus.OK);

	}

	public MessageResponse updateTicketStatus(String moviename) throws TicketNotFoundException, MovieNotFoundException {
		Optional<Movies> movie = moviesRepository.findByMovienameOrderByMovienameAsc(moviename);
		if (movie.isPresent()) {
			Movies existingMovie = movie.get();

			if (existingMovie.getNoOfTicketsAllotted() == 0) {
				existingMovie.setStatus("SOLD OUT");
			} else if (existingMovie.getNoOfTicketsAllotted() < 10) {
				existingMovie.setStatus("BOOK ASAP");
			} else {
				existingMovie.setStatus("Available");
			}
			moviesRepository.save(existingMovie);
		} else {
			throw new MovieNotFoundException("Movie not found with the name: " + moviename);
		}

		return new MessageResponse("Tickets Updated Successfully", HttpStatus.OK);
	}

	private List<MoviesDTO> convertToDTOList(List<Movies> movies) {
		return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private MoviesDTO convertToDTO(Movies movie) {

		MoviesDTO moviesDto = new MoviesDTO();
		moviesDto.setMoviename(movie.getMoviename());
		moviesDto.setTheatrename(movie.getTheatrename());
		moviesDto.setNoOfTicketsAllotted(movie.getNoOfTicketsAllotted());
		moviesDto.setStatus(movie.getStatus());
		moviesDto.setGenre(movie.getGenre());
		moviesDto.setAbout(movie.getAbout());
		moviesDto.setPosterURL(movie.getPosterURL());
		moviesDto.setPrice(movie.getPrice());
		moviesDto.setReleaseDate(movie.getReleaseDate());

		return moviesDto;

	}

}
