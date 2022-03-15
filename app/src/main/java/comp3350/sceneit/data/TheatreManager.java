package comp3350.sceneit.data;

import java.util.ArrayList;

import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.exceptions.TheatreNotFoundException;

public interface TheatreManager {
    /**
     * Get the list of theatres in the DB.
     *
     * @return the list of theatres in the DB
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Theatre
     */
    ArrayList<Theatre> getTheatres() throws DatabaseAccessException;

    /**
     * @param theatre_id The ID of the theatre to retrieve.
     * @return The theatre with the given ID.
     * @throws DatabaseAccessException  in the case the database fails to be accessed.
     * @throws TheatreNotFoundException in the case the theatre is not found in the DB.
     * @see Theatre
     */
    Theatre getTheatre(int theatre_id) throws DatabaseAccessException, TheatreNotFoundException;

}
