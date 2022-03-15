package comp3350.sceneit.presentation;

//Class to store and get the movie titles and ratings
//to display when searching
public class SearchItem {
    private String mTitle;
    private String mRating;

    public SearchItem(String mTitle, String mRating){
        this.mTitle = mTitle;
        this.mRating = mRating;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getRating() {
        return mRating;
    }
}
