package comp3350.sceneit.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Collections;
import java.util.Random;

import comp3350.sceneit.R;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.Movie;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.logic.MovieFiltering;

public class MainActivity extends AppCompatActivity implements IMovieClickListener {

    private static final String TAG = "MainActivity";

    //Now playing
    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mRating = new ArrayList<>();
        private ArrayList<Integer> mvID = new ArrayList<>();
    //Trending
    private ArrayList<String> tImageURL = new ArrayList<>();
    private ArrayList<String> tTitle = new ArrayList<>();
    private ArrayList<String> tRating = new ArrayList<>();

    private Set<String> mgeneres  =  new HashSet<String>();
    private ArrayList<Movie> movies = new ArrayList<>();

    //Theatres
    private String[] theatre = {"SilverCity St. Vital Cinemas", "Landmark Cinemas Towne Cinema 8", "Scotiabank Theatre",
            "Cineplex Odeon McGillivray", "Famous Players Cinemas", "Cinema City Northgate",
            "Cinematheque", "Burton Cummings Theatre", "Landmark Cinemas Garry Theatre"};

    private RecyclerView recyclerView;
    private TextView tvTheatreLocation;
    private DatabaseManager dbm;
    private MovieFiltering mvFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTheatreLocation = (TextView) findViewById(R.id.tvTheaterName);
        tvTheatreLocation.setText(theatre[0]);
        dbm = new PostgresDatabaseManager();

        getNowPlayingDetails();
        getTrending();

        mvFilter = new MovieFiltering();
        mvFilter.setMovies(movies);

        //Create spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        String[] array = mgeneres.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayFiltering(parent.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //OnClickListener for ivPin
        ImageView imgPin = (ImageView) findViewById(R.id.ivPin);
        imgPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTheatre();
            }
        });

        //OnClickListener for tvTheaterName
        TextView txtTheaterName = (TextView) findViewById(R.id.tvTheaterName);
        txtTheaterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTheatre();
            }
        });

        //OnClickListener for search
        ImageView imgSearch = (ImageView) findViewById(R.id.ivSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);

                //Send data to SearchActivity
                i.putStringArrayListExtra("mTitle", mTitle);
                i.putStringArrayListExtra("mRating", mRating);
                i.putExtra("theater", tvTheatreLocation.getText().toString());

                startActivity(i);
            }
        });

    }

    public void displayFiltering(String filter) {
        mvFilter.setSelectedGen(filter);
        mvFilter.doFiltering();
        display(R.id.rvNowPlaying, 0);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mvFilter.getFilteredURL(), mvFilter.getFilteredTittle(), mvFilter.getFilteredRating(), mvFilter.getFilteredId(), R.layout.now_playing_carousel_item, this);
        recyclerView.setAdapter(adapter);
    }
    private void getNowPlayingDetails(){
        try
        {
            movies = dbm.getMovies();
            for(Movie mv : movies)
            {
                mImageURL.add(mv.getPoster_url());
                mgeneres.addAll(mv.getTags());
                mTitle.add(mv.getTitle());
                mvID.add(mv.getMovieId());
                mRating.add(convertRating(mv.getRating()));
            }
        } catch (DatabaseAccessException e)
        {
            e.printStackTrace();
        }

        display(R.id.rvNowPlaying, 50);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageURL, mTitle, mRating, mvID, R.layout.now_playing_carousel_item, this);
        recyclerView.setAdapter(adapter);
    }

    private void getTrending() {
        ArrayList<Movie> movies;
        try
        {
            movies = dbm.getMovies();
            for(Movie mv : movies)
            {
                tImageURL.add(mv.getPoster_url());
                tTitle.add(mv.getTitle());
                tRating.add(convertRating(mv.getRating()));
            }
        } catch (DatabaseAccessException e)
        {
            e.printStackTrace();
        }

        long seed = System.nanoTime();
        Collections.shuffle(tImageURL, new Random(seed));
        Collections.shuffle(tTitle, new Random(seed));
        Collections.shuffle(tRating, new Random(seed));

        display(R.id.rvTrending, 50);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(this, tImageURL, tTitle, tRating, mvID, R.layout.trending_carousel_item, this);
        recyclerView.setAdapter(adapter1);
    }

    private void display(int carouselDisplayType, int spacing) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(carouselDisplayType);
        recyclerView.setLayoutManager(layoutManager);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(spacing); //spacing between movies
        recyclerView.addItemDecoration(itemDecorator);
    }

    private void chooseTheatre() {
        tvTheatreLocation = (TextView) findViewById(R.id.tvTheaterName);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Theater");
        builder.setItems(theatre, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvTheatreLocation.setText(theatre[which]);
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }

    //convert rating range from 0 - 100 to 0 - 5
    private String convertRating(int value)
    {
        double newRating = value * 0.05;
        return String.valueOf(newRating);
    }

    @Override
    public void movieClick(int position, int carouselViewType) {
        Bundle movieDetails = new Bundle();

        //check which recyclerView is being clicked before passing data
        if(carouselViewType == R.layout.now_playing_carousel_item) {
            movieDetails.putString("movieTitle", mTitle.get(position));
            movieDetails.putString("moviePoster", mImageURL.get(position));
            movieDetails.putString("movieRating", mRating.get(position));

            Log.d(TAG, "Data passed: " + mTitle.get(position) + " " + mImageURL.get(position) +
                    " " + mRating.get(position) + " " + tvTheatreLocation.getText().toString());
        }
        else {
            movieDetails.putString("movieTitle", tTitle.get(position));
            movieDetails.putString("moviePoster", tImageURL.get(position));
            movieDetails.putString("movieRating", tRating.get(position));

            Log.d(TAG, "Trending data passed: " + tTitle.get(position) + " " + tImageURL.get(position) +
                    " " + tRating.get(position) + " " + tvTheatreLocation.getText().toString());
        }

        movieDetails.putString("theater", tvTheatreLocation.getText().toString());

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtras(movieDetails);
        this.startActivity(intent);
    }

}