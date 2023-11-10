package dev.natrix.movies.service;

import dev.natrix.movies.entity.Movie;
import dev.natrix.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findMovieByImdbId(String imdbId) {
        return movieRepository.findByImdbIdContaining(imdbId);
    }

    public List<Movie> findMovieByGenre(String genre) {
        return movieRepository.findByGenres(genre);
    }


    public List<Movie> findMovieByTitle(String title) {
        // Convert the input to lowercase before searching
        String lowercaseName = title.toLowerCase();
        // Convert all stored titles to lowercase for case-insensitive comparison
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(lowercaseName))
                .collect(Collectors.toList());
    }
}
