package dev.natrix.movies.repository;

import dev.natrix.movies.entity.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {

    Optional<Movie> findByImdbIdContaining(String imdbId);

    @Query("{'genres': { $regex: ?0, $options: 'i' }}")
    List<Movie> findByGenres(String genre);
}
