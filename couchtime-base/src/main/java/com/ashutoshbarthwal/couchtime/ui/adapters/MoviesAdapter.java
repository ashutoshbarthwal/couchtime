package com.ashutoshbarthwal.couchtime.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ashutoshbarthwal.couchtime.R;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.utils.GenreHelper;
import com.ashutoshbarthwal.couchtime.utils.ImageLoadingUtils;
import com.ashutoshbarthwal.couchtime.utils.LocalStoreUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public interface Callbacks {
        public void onMovieClick(Movie movies);
        public void onFavoriteClick(Movie movies);
    }

    private Callbacks mCallbacks;
    private Context context;
    private List<Movie> mFeedList;

    public MoviesAdapter(List<Movie> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = View.inflate(parent.getContext(), R.layout.item_movie, null);
        //view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

            final Movie movies = mFeedList.get(position);

            final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

            movieViewHolder.mMovieName.setText(movies.getOriginal_title());
            movieViewHolder.mMovieGenre.setText(GenreHelper.getGenreNamesList(movies.getGenres()).trim());

            ImageLoadingUtils.load(movieViewHolder.mMovieImage, "https://image.tmdb.org/t/p/w185/" + movies.getPoster_path());

            if(LocalStoreUtil.hasInFavorites(context, movies.getId())) {
                movieViewHolder.mFavoriteButton.setSelected(true);
                movies.setFavorite(true);
            } else {
                movieViewHolder.mFavoriteButton.setSelected(false);
                movies.setFavorite(false);
            }

            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mCallbacks!=null) {
                        mCallbacks.onMovieClick(movies);
                    }
                }
            });

            movieViewHolder.mFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mCallbacks!=null) {
                        movieViewHolder.mFavoriteButton.setSelected(!movies.isFavorite());
                        mCallbacks.onFavoriteClick(movies);
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieName, mMovieGenre;
        private SimpleDraweeView mMovieImage;
        private ImageButton mFavoriteButton;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieName = (TextView) itemView.findViewById(R.id.movieTextView);
            mMovieGenre = (TextView) itemView.findViewById(R.id.movieGenre);
            mMovieImage = (SimpleDraweeView) itemView.findViewById(R.id.movieImage);
            mFavoriteButton = (ImageButton) itemView.findViewById(R.id.movie_item_btn_favorite);
        }
    }

}
