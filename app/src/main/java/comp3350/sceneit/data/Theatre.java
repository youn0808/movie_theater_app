package comp3350.sceneit.data;

public class Theatre {
    int theatreId;
    String name;
    String location;

    public Theatre(int theatreId, String name, String location) {
        this.theatreId = theatreId;
        this.name = name;
        this.location = location;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
