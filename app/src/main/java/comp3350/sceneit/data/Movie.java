package comp3350.sceneit.data;

import java.util.List;

public class Movie {
    int movieId;
    String title;
    String description;
    int rating;
    String poster_url;
    List<String> tags;

    public Movie(int movieId, String title, String description, int rating, String poster_url, List<String> tags) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.poster_url = poster_url;
        this.tags = tags;
    }

    /**
     * @return The rating of the movie out of 100. Ex. 75
     */
    public int getRating() {
        return rating;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() { return tags; }
}
