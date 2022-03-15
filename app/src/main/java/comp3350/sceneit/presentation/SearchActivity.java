package comp3350.sceneit.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

import comp3350.sceneit.R;

public class SearchActivity extends AppCompatActivity implements IMovieClickListener{

    private static final String TAG = "SearchActivity";

    private RecyclerView recyclerViewSearch;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<SearchItem> movieList;
    private ArrayList<String> titles;
    private ArrayList<String> ratings;
    private String selectedTheater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        createMovieList();  //populate the list with the movies title and rating
        buildRecyclerView(); //build and populate the recycleView

        //search for the movie using the title
        EditText txtSearch = findViewById(R.id.etSearch);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void createMovieList() {
        movieList = new ArrayList<>();

        titles = getIntent().getExtras().getStringArrayList("mTitle");
        ratings = getIntent().getExtras().getStringArrayList("mRating");
        selectedTheater = getIntent().getExtras().getString("theater");

        for (int i = 0; i < titles.size(); i++)
        {
            movieList.add(new SearchItem(titles.get(i), ratings.get(i)));
        }
    }

    private void buildRecyclerView() {
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        recyclerViewSearch.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchAdapter(movieList, this);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10); //spacing between movies
        recyclerViewSearch.addItemDecoration(itemDecorator);

        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setAdapter(mAdapter);
    }

    private void filter(String text)
    {
        ArrayList<SearchItem> filteredList = new ArrayList<>();

        //if we have the movie add it to the filtered list
        for(SearchItem item : movieList){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    @Override
    public void movieClick(int position, int carouselViewType) {
        Bundle movieDetails = new Bundle();

        movieDetails.putString("movieTitle", titles.get(position));
        movieDetails.putString("movieRating", ratings.get(position));

        movieDetails.putString("theater", selectedTheater);

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtras(movieDetails);
        this.startActivity(intent);
    }
}

