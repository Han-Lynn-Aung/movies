package dev.natrix.movies.service;

import dev.natrix.movies.entity.Movie;
import dev.natrix.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();

    }
}
