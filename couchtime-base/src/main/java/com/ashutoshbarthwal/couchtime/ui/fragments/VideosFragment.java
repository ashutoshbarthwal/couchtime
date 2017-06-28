package com.ashutoshbarthwal.couchtime.ui.fragments;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
import com.ashutoshbarthwal.couchtime.event.MovieVideosEvent;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.models.tmdb.TMDBResponse;
import com.ashutoshbarthwal.couchtime.models.videos.Video;
import com.ashutoshbarthwal.couchtime.ui.adapters.VideosAdapter;
import com.ashutoshbarthwal.couchtime.ui.views.GridSpacingItemDecoration;
import com.ashutoshbarthwal.couchtime.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashutosh on 04-03-2017.
 */
public class VideosFragment extends BaseFragment implements  VideosAdapter.Callbacks{

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private VideosAdapter videosAdapter;
    private List<Video> mVideos = new ArrayList<>();
    private Movie movies;
    private static Movie mMovie;

    public static VideosFragment newInstance(Movie movies){
        if (movies == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        mMovie = movies;
        VideosFragment fragment = new VideosFragment();
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
            Call<Movie> callGetMovies = apiService.getSingleMovie(movies.getId(),BuildConfig.TMDB_API_KEY,"videos");
            callGetMovies.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getVideos().getResults().size()>0)
                        {
                            initAdapter(response.body().getVideos().getResults());
                            EventBus.getDefault().post(new MovieVideosEvent(response.body().getVideos().getResults().get(0)));
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
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        int columnCount = getResources().getInteger(R.integer.videos_columns);

        gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        int spacing = Utils.dpToPx(5, getActivity()); // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(columnCount, spacing, includeEdge));
        recyclerView.setLayoutManager(gridLayoutManager);
        initAdapter(mVideos);
        videosAdapter.notifyDataSetChanged();
    }

    private void initAdapter(List<Video> movieVideos) {
        videosAdapter = new VideosAdapter(movieVideos);
        videosAdapter.setCallbacks(this);
        recyclerView.setAdapter(videosAdapter);
    }


    @Override
    public void onVideoClick(Video movieVideos) {

        inflateVideoPlayer(movieVideos.getKey());

    }

    private void inflateVideoPlayer(String videoKey) {

        int startTimeMillis = 0;
        boolean autoplay = true;
        boolean lightboxMode = false;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoKey));
        intent.putExtra("force_fullscreen",true);

        if (intent != null) {
            if (canResolveIntent(intent)) {
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            } else {
                // Could not resolve the intent - must need to install or update the YouTube API service.
            }
        }
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }
}
