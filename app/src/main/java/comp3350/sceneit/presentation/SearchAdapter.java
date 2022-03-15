package comp3350.sceneit.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.sceneit.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<SearchItem> movieList;
    private IMovieClickListener mMovieClickListener;

    public SearchAdapter(ArrayList<SearchItem> movieList, IMovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.mMovieClickListener = movieClickListener;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView movieTitle;
        public RatingBar movieRating;
        IMovieClickListener movieClickListener;

        public SearchViewHolder(View itemView, IMovieClickListener movieClickListener) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.tvmovieTitleSearch);
            movieRating = itemView.findViewById(R.id.rbMovieRatingSearch);
            this.movieClickListener = movieClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieClickListener.movieClick(getAdapterPosition(), 0);
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(v, mMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchItem currItem = movieList.get(position);

        holder.movieTitle.setText(currItem.getTitle());
        holder.movieRating.setRating(Float.parseFloat(currItem.getRating()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void filterList(ArrayList<SearchItem> filteredList) {
        movieList = filteredList;
        notifyDataSetChanged(); //refresh the recyclerView to show what's searched
    }
}
