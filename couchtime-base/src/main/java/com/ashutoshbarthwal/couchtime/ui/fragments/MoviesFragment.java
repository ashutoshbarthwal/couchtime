package com.ashutoshbarthwal.couchtime.ui.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashutoshbarthwal.couchtime.BuildConfig;
import com.ashutoshbarthwal.couchtime.R;
import com.ashutoshbarthwal.couchtime.core.BaseFragment;
import com.ashutoshbarthwal.couchtime.core.api.ApiModule;
import com.ashutoshbarthwal.couchtime.core.api.ApiServiceGenerator;
import com.ashutoshbarthwal.couchtime.database.MoviesContract;
import com.ashutoshbarthwal.couchtime.database.MoviesOpenHelper;
import com.ashutoshbarthwal.couchtime.event.FavoriteChangeEvent;
import com.ashutoshbarthwal.couchtime.event.MovieSelectedEvent;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.models.sort.Sort;
import com.ashutoshbarthwal.couchtime.models.tmdb.TMDBResponse;
import com.ashutoshbarthwal.couchtime.ui.adapters.MoviesAdapter;
import com.ashutoshbarthwal.couchtime.ui.views.EndlessRecyclerView;
import com.ashutoshbarthwal.couchtime.ui.views.GridSpacingItemDecoration;
import com.ashutoshbarthwal.couchtime.utils.LocalStoreUtil;
import com.ashutoshbarthwal.couchtime.utils.Utils;
import com.ashutoshbarthwal.couchtime.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ashutoshbarthwal.couchtime.models.sort.Sort.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends BaseFragment implements MoviesAdapter.Callbacks{

    private static final String TAG_SORT = "state_sort";

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MoviesAdapter moviesAdapter;
    private List<Movie> mMovies = new ArrayList<>();
    private Sort mSort;
    private int currentPage, totalPages;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        super.onDetach();
    }

    public static MoviesFragment newInstance(@NonNull Sort sort) {
        Bundle args = new Bundle();
        args.putSerializable(TAG_SORT, sort);

        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSort = (Sort) getArguments().getSerializable(TAG_SORT);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        int columnCount = getResources().getInteger(R.integer.movies_columns);

        gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        int spacing = Utils.dpToPx(5, getActivity()); // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(columnCount, spacing, includeEdge));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerView(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if(currentPage<totalPages) {

                    getMoviesData(mSort, currentPage+1);

                }

            }
        });

        moviesAdapter = new MoviesAdapter(mMovies);
        moviesAdapter.setCallbacks(this);
        recyclerView.setAdapter(moviesAdapter);

        getMoviesData(mSort, 1);
    }

    /*private void initAdapter(List<Movies> movies) {

        moviesAdapter = new MoviesAdapter(movies);
        moviesAdapter.setCallbacks(this);
        recyclerView.setAdapter(moviesAdapter);
    }*/

    public void getMoviesData(Sort sort, final int currentPageMovie)
    {
        if(isInternetAvailable()) {
            ApiModule apiService = ApiServiceGenerator.createService(ApiModule.class);
            Call<TMDBResponse> callGetMovies;
            switch (sort) {
                case POPULAR:
                callGetMovies = apiService.getPopularMovies(BuildConfig.TMDB_API_KEY,"videos,reviews",currentPageMovie); break;
                case TOP_RATED:
                callGetMovies = apiService.getTopRatedMovies(BuildConfig.TMDB_API_KEY,"videos,reviews",currentPageMovie); break;
                case UPCOMING:
                callGetMovies = apiService.getUpcomingMovies(BuildConfig.TMDB_API_KEY,"videos,reviews",currentPageMovie); break;
                default:
                callGetMovies = apiService.getUpcomingMovies(BuildConfig.TMDB_API_KEY,"videos,reviews",currentPageMovie); break;
            }

            callGetMovies.enqueue(new Callback<TMDBResponse>() {
                @Override
                public void onResponse(Call<TMDBResponse> call, Response<TMDBResponse> response) {
                    if(response.isSuccessful() && response.body()!=null)
                    {
                        hideProgressDialog();
                        TMDBResponse responseData = response.body();
                        currentPage = responseData.getPage();
                        totalPages = responseData.getTotal_pages();
                        List<Movie> movies = responseData.getResults();
                        mMovies.addAll(movies);
                        moviesAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<TMDBResponse> call, Throwable t) {
                    hideProgressDialog();
                }
            });

            showProgressDialog();
        } else {
            Snackbar snackbar = Snackbar
                    .make(getCoordinatorLayout(), R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getMoviesData(mSort, currentPage);
                        }
                    });
            snackbar.setActionTextColor(ContextCompat.getColor(getContext(),R.color.colorBlue));


            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }


    @Override
    public void onMovieClick(Movie movies) {
        EventBus.getDefault().post(new MovieSelectedEvent(movies));
    }

    @Override
    public void onFavoriteClick(Movie movies) {

        if(movies.isFavorite()) { // Already added is removed
            LocalStoreUtil.removeFromFavorites(getActivity(), movies.getId());
            ViewUtils.showToast(getResources().getString(R.string.removed_favorite),getActivity());

            getActivity().getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movies.getId())).build(), null, null);

        } else {
            LocalStoreUtil.addToFavorites(getActivity(), movies.getId());
            ViewUtils.showToast(getResources().getString(R.string.added_favorite),getActivity());

            ContentValues values = MoviesOpenHelper.getMovieContentValues(movies);
            getActivity().getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, values);
        }

        moviesAdapter.notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final FavoriteChangeEvent event){
        Log.e("onEvent","->"+event.isFavoriteChanged());

        moviesAdapter.notifyDataSetChanged();

    }
}
