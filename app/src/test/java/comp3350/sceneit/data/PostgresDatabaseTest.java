package comp3350.sceneit.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.sceneit.data.exceptions.AiringNotFoundException;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.exceptions.MovieNotFoundException;
import comp3350.sceneit.data.exceptions.TheatreNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PostgresDatabaseTest {
    @Test
    public void listMovies() {
        DatabaseManager dbm = new PostgresDatabaseManager();
        ArrayList<Movie> movies;
        try {
            movies = dbm.getMovies();
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
            return;
        }

        assertFalse(movies.isEmpty());
        boolean movie_found = false;
        for (Movie movie : movies) {
            if (movie.getMovieId() == 1) {
                movie_found = true;
                assertEquals(movie.getTitle(), "The Avengers");
                assertEquals(movie.getDescription(), "When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!");
            }
        }

        assertTrue(movie_found);
    }


    @Test
    public void listTheatres() {
        DatabaseManager dbm = new PostgresDatabaseManager();
        ArrayList<Theatre> theatres;
        try {
            theatres = dbm.getTheatres();
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
            return;
        }

        assertFalse(theatres.isEmpty());
        Theatre theatre = theatres.iterator().next();

        assertEquals(theatre.getTheatreId(), 1);
        assertEquals(theatre.getName(), "SilverCity St. Vital");
        assertEquals(theatre.getLocation(), "160-1225 St Mary's Rd, Winnipeg, MB R2M 5E5");
    }

    @Test
    public void listAirings() {
        DatabaseManager dbm = new PostgresDatabaseManager();
        ArrayList<Airing> airings;
        try {
            airings = dbm.getAirings(1);
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
            return;
        }

        assertFalse(airings.isEmpty());
        Airing airing = airings.iterator().next();

        Calendar cal = new GregorianCalendar();
        cal.set(2021, 2, 28, 17, 30);

        assertEquals(airing.getAiringId(), 1);
        assertEquals(airing.getMovieId(), 1);
        assertEquals(airing.getTheatreId(), 1);
        assertEquals(airing.getTotalSeats(), 100);
        assertEquals(airing.getAvailableSeats(), 100);

        // Verify that the same function with the input signature of a Movie returns the same result
        try {
            Movie movie = new Movie(
                    1,
                    "The Avengers",
                    "When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!",
                    77,
                    "https://image.tmdb.org/t/p/original/RYMX2wcKCBAr24UyPD7xwmjaTn.jpg",
                    new ArrayList<String>() {{
                        add("Science Fiction");
                        add("Action");
                        add("Adventure");
                    }}
            );

            ArrayList<Airing> airings2 = dbm.getAirings(movie);
            Airing airing2 = airings2.iterator().next();

            assertEquals(airing, airing2);
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
        }
    }

    @Test
    public void getMovie() {
        DatabaseManager dbm = new PostgresDatabaseManager();
        Movie movie;
        try {
            movie = dbm.getMovie(1);
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
            return;
        } catch (MovieNotFoundException e) {
            fail(String.format("Couldn't find expected movie: %s", e.getMessage()));
            return;
        }

        assertEquals(movie.getMovieId(), 1);
        assertEquals(movie.getTitle(), "The Avengers");
        assertEquals(movie.getDescription(), "When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!");
    }

    @Test
    public void getMovieNotFound() {
        DatabaseManager dbm = new PostgresDatabaseManager();

        assertThrows(MovieNotFoundException.class, () -> {
            dbm.getMovie(-1);
        });
    }

    @Test
    public void getTheatre() {
        DatabaseManager dbm = new PostgresDatabaseManager();
        Theatre theatre;
        try {
            theatre = dbm.getTheatre(1);
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
            return;
        } catch (TheatreNotFoundException e) {
            fail(String.format("Couldn't find expected theatre: %s", e.getMessage()));
            return;
        }

        assertEquals(theatre.getTheatreId(), 1);
        assertEquals(theatre.getName(), "SilverCity St. Vital");
        assertEquals(theatre.getLocation(), "160-1225 St Mary's Rd, Winnipeg, MB R2M 5E5");
    }

    @Test
    public void getTheatreNotFound() {
        DatabaseManager dbm = new PostgresDatabaseManager();

        assertThrows(TheatreNotFoundException.class, () -> {
            dbm.getTheatre(-1);
        });
    }

    @Test
    public void getAiring() {
        DatabaseManager dbm = new PostgresDatabaseManager();
        Airing airing;
        try {
            airing = dbm.getAiring(1);
        } catch (DatabaseAccessException e) {
            fail(String.format("Error accessing database: %s", e.getMessage()));
            return;
        } catch (AiringNotFoundException e) {
            fail(String.format("Couldn't find expected airing: %s", e.getMessage()));
            return;
        }

        Calendar cal = new GregorianCalendar();
        cal.set(2021, 2, 28, 17, 30);

        assertEquals(airing.getAiringId(), 1);
        assertEquals(airing.getMovieId(), 1);
        assertEquals(airing.getTheatreId(), 1);
        assertEquals(airing.getTotalSeats(), 100);
        assertEquals(airing.getAvailableSeats(), 100);
    }

    @Test
    public void getAiringNotFound() {
        DatabaseManager dbm = new PostgresDatabaseManager();

        assertThrows(AiringNotFoundException.class, () -> {
            dbm.getAiring(-1);
        });
    }
}