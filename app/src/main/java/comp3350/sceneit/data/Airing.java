package comp3350.sceneit.data;

import java.sql.Date;

public class Airing {
    int airingId;
    int movieId;
    int theatreId;
    Date airTime;
    int totalSeats;
    int availableSeats;

    public Airing(int airingId, int movieId, int theatreId, Date airTime, int totalSeats, int availableSeats) {
        this.airingId = airingId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.airTime = airTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public int getAiringId() {
        return airingId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public Date getAirTime() {
        return airTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airing airing = (Airing) o;
        return airingId == airing.airingId &&
                movieId == airing.movieId &&
                theatreId == airing.theatreId &&
                totalSeats == airing.totalSeats &&
                availableSeats == airing.availableSeats &&
                airTime.equals(airing.airTime);
    }
}
