package comp3350.sceneit.data;

import java.util.ArrayList;

import comp3350.sceneit.data.exceptions.AiringNotFoundException;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;

public interface AiringManager {
    /**
     * Get the list of airings for a given movie.
     *
     * @param movie_id The ID of the movie whose airings we want.
     * @return The list of airings for a given movie
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Movie
     * @see Airing
     */
    ArrayList<Airing> getAirings(int movie_id) throws DatabaseAccessException;

    /**
     * @param movie The movie to retrieve airings for.
     * @return The airings of the given movie.
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Movie
     * @see Airing
     */
    default ArrayList<Airing> getAirings(Movie movie) throws DatabaseAccessException {
        return this.getAirings(movie.getMovieId());
    }

    /**
     * @param airing_id The ID of the airing to retrieve.
     * @return The airing with the given ID
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @throws AiringNotFoundException if the airing is not found in the DB.
     * @see Airing
     */
    Airing getAiring(int airing_id) throws DatabaseAccessException, AiringNotFoundException;

}
