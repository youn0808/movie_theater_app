package comp3350.sceneit.logic;

import java.util.ArrayList;

import comp3350.sceneit.R;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.Movie;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.presentation.RecyclerViewAdapter;

public class MovieFiltering {
    private ArrayList<Movie> movies = new ArrayList<>();

    private ArrayList<Movie> filteredMovies = new ArrayList<>();
    private ArrayList<String> filteredURL = new ArrayList<>();
    private ArrayList<String> filteredRating = new ArrayList<>();
    private ArrayList<String> filteredTittle = new ArrayList<>();
    private ArrayList<Integer> filteredId = new ArrayList<>();
    private String selectedGen;

    public MovieFiltering() {
    }

    private void resetFilteredArray() {
        filteredTittle.clear();
        filteredRating.clear();
        filteredURL.clear();
        filteredId.clear();
    }

    public void doFiltering() {
        resetFilteredArray();
        int mvPosition = 0;
        for (Movie mv : movies) {
            if (mv.getTags().contains(selectedGen)) {
                filteredTittle.add(mv.getTitle());
                filteredId.add(mvPosition);
                filteredRating.add(convertRating(mv.getRating()));
                filteredURL.add(mv.getPoster_url());
            }
            mvPosition++;
        }
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    public void setFilteredMovies(ArrayList<Movie> filteredMovies) {
        this.filteredMovies = filteredMovies;
    }

    public ArrayList<String> getFilteredURL() {
        return filteredURL;
    }

    public void setFilteredURL(ArrayList<String> filteredURL) {
        this.filteredURL = filteredURL;
    }

    public ArrayList<String> getFilteredRating() {
        return filteredRating;
    }

    public void getFilteredRating(ArrayList<String> getFilteredRating) {
        this.filteredRating = filteredRating;
    }

    public ArrayList<String> getFilteredTittle() {
        return filteredTittle;
    }

    public void setFilteredTittle(ArrayList<String> filteredTittle) {
        this.filteredTittle = filteredTittle;
    }

    public ArrayList<Integer> getFilteredId() {
        return filteredId;
    }

    public void setFilteredId(ArrayList<Integer> filteredId) {
        this.filteredId = filteredId;
    }

    public String getSelectedGen() {
        return selectedGen;
    }

    public void setSelectedGen(String selectedGen) {
        this.selectedGen = selectedGen;
    }

    private String convertRating(int value) {
        double newRating = value * 0.05;
        return String.valueOf(newRating);
    }

}


