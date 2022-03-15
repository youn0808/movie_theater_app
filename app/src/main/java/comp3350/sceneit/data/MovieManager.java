package comp3350.sceneit.data;

import java.util.ArrayList;

import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.exceptions.MovieNotFoundException;

public interface MovieManager {
    /**
     * Get the list of movies in the DB
     *
     * @return The list af movies in the DB
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Movie
     */
    ArrayList<Movie> getMovies() throws DatabaseAccessException;

    /**
     * Retrieves a movie based on its ID.
     *
     * @param movie_id The ID of the movie to retrieve.
     * @return The movie with the given the ID.
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @throws MovieNotFoundException  if the movie is not found in the DB.
     * @see Movie
     */
    Movie getMovie(int movie_id) throws DatabaseAccessException, MovieNotFoundException;
}
