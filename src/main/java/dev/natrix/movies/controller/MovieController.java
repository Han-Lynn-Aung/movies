package dev.natrix.movies.controller;

import dev.natrix.movies.entity.Movie;
import dev.natrix.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<List<Movie>>(movieService.allMovies(),
                HttpStatus.OK);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<List<Movie>> getMovie(@PathVariable String identifier) {
        if (isImdbId(identifier)) {
            Optional<Movie> movie = movieService.findMovieByImdbId(identifier);
            return movie.map(value -> new ResponseEntity<>(Collections.singletonList(value), HttpStatus.OK))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else if (isGenre(identifier)) {
            return new ResponseEntity<>(movieService.findMovieByGenre(identifier), HttpStatus.OK);
        } else {
            List<Movie> movies = movieService.findMovieByTitle(identifier);
            return movies.isEmpty() ? ResponseEntity.notFound().build() : new ResponseEntity<>(movies, HttpStatus.OK);
        }
    }

    private boolean isImdbId(String identifier) {
        // IMDb ID pattern: "tt" followed by at least 7 digits
        String imdbIdPattern = "tt\\d{7,}";
        // Check if the identifier matches the IMDb ID pattern
        return identifier.matches(imdbIdPattern);
    }

    private boolean isGenre(String identifier) {
        List<String> validGenres = Arrays.asList("Action", "Drama", "Comedy", "Thriller", "Romance", "Horror",
                "Science Fiction", "Fantasy", "Adventure", "Mystery", "Crime", "Animation", "Family", "Documentary",
                "Biography", "History", "War", "Musical", "Western", "Sport");

        String trimmedIdentifier = identifier.trim();

        for (String genre : validGenres) {
            String trimmedGenre = genre.trim();
            boolean result = trimmedGenre.equalsIgnoreCase(trimmedIdentifier);
            if (result) {
                return true;
            }
        }
        return false;
    }
}
