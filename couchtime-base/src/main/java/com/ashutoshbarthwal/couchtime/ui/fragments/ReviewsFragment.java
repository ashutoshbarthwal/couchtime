package com.ashutoshbarthwal.couchtime.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ashutoshbarthwal.couchtime.BuildConfig;
import com.ashutoshbarthwal.couchtime.R;
import com.ashutoshbarthwal.couchtime.core.BaseFragment;
import com.ashutoshbarthwal.couchtime.core.api.ApiModule;
import com.ashutoshbarthwal.couchtime.core.api.ApiServiceGenerator;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.models.reviews.Review;
import com.ashutoshbarthwal.couchtime.ui.adapters.ReviewsAdapter;
import com.ashutoshbarthwal.couchtime.ui.views.VerticalSpaceItemDecoration;
import com.ashutoshbarthwal.couchtime.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashutosh on 04-03-2017.
 */
public class ReviewsFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ReviewsAdapter reviewsAdapter;
    private List<Review> mReviews = new ArrayList<>();
    private Movie movies;
    private View noReviewsView;
    private static Movie mMovie;

    public static ReviewsFragment newInstance(Movie movies){
        if (movies == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        mMovie = movies;
        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movies = mMovie;
        getVideosData();
    }

    public void getVideosData() {
        if(isInternetAvailable()) {
            ApiModule apiService = ApiServiceGenerator.createService(ApiModule.class);
            Call<Movie> callGetMovies = apiService.getSingleMovie(movies.getId(), BuildConfig.TMDB_API_KEY,"reviews");
            callGetMovies.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getReviews().getResults().size()>0)
                        {
                            initAdapter(response.body().getReviews().getResults());
                        }
                        else
                        {
                            initAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                }
            });
        } else {
            Snackbar snackbar = Snackbar
                    .make(getCoordinatorLayout(), R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getVideosData();
                        }
                    });
            snackbar.setActionTextColor(ContextCompat.getColor(getContext(),R.color.colorBlue));


            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        noReviewsView = view.findViewById(R.id.layout_no_reviews);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        int spacing = Utils.dpToPx(5, getActivity());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacing));
        initAdapter(mReviews);
    }

    private void initAdapter(List<Review> movieReviews) {
        showNoReviews(false);
        reviewsAdapter = new ReviewsAdapter(movieReviews);
        recyclerView.setAdapter(reviewsAdapter);
    }

    private void initAdapter() {
        showNoReviews(true);
    }

    private void showNoReviews(boolean value){

        int noReviewsVisibility = value? View.VISIBLE : View.GONE;
        noReviewsView.setVisibility(noReviewsVisibility);

        int recyclerViewVisibility = value? View.GONE : View.VISIBLE;
        recyclerView.setVisibility(recyclerViewVisibility);
    }
}
