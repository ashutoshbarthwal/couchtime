package com.ashutoshbarthwal.couchtime.ui.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashutoshbarthwal.couchtime.R;
import com.ashutoshbarthwal.couchtime.core.BaseActivity;
import com.ashutoshbarthwal.couchtime.database.MoviesContract;
import com.ashutoshbarthwal.couchtime.database.MoviesOpenHelper;
import com.ashutoshbarthwal.couchtime.event.FavoriteChangeEvent;
import com.ashutoshbarthwal.couchtime.event.MovieVideosEvent;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.models.videos.Video;
import com.ashutoshbarthwal.couchtime.ui.adapters.MoviesDetailsAdapter;
import com.ashutoshbarthwal.couchtime.utils.GenreHelper;
import com.ashutoshbarthwal.couchtime.utils.ImageLoadingUtils;
import com.ashutoshbarthwal.couchtime.utils.LocalStoreUtil;
import com.ashutoshbarthwal.couchtime.utils.ShareUtils;
import com.ashutoshbarthwal.couchtime.utils.Utils;
import com.ashutoshbarthwal.couchtime.utils.ViewUtils;
import com.ashutoshbarthwal.couchtime.widgets.AppBarStateChangeListener;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class MovieDetailsActivity extends BaseActivity {

    private Movie movies;
    private SimpleDraweeView mHeaderImage, mMoviePosterImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TextView mMovieTitle, mMovieGenre;
    private FloatingActionButton mFavoriteButton;

    private ViewPager mViewPager;
    private MoviesDetailsAdapter mMoviesDetailsAdapter;
    private TabLayout tabLayout;
    private boolean isFavoriteChanged = false;
    private Video movieVideos;

    //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

    //Review and Trailers

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getIntentData();

        initViews();
        inflateData();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent!=null) {
            movies = (Movie) intent.getExtras().getParcelable(Movie.TAG_MOVIES);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mFavoriteButton = (FloatingActionButton) findViewById(R.id.favButton);
        mHeaderImage = (SimpleDraweeView) findViewById(R.id.headerImage);
        mMoviePosterImage = (SimpleDraweeView) findViewById(R.id.moviePosterImage);
        mMovieTitle = (TextView) findViewById(R.id.movieTitle);
        mMovieGenre = (TextView) findViewById(R.id.movieGenre);

        setupTabLayout();
    }

    private void setupTabLayout() {
        mMoviesDetailsAdapter = new MoviesDetailsAdapter(getSupportFragmentManager(), movies);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mMoviesDetailsAdapter);

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorGrey100), getResources().getColor(R.color.primaryText));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.primaryText));
        tabLayout.setSelectedTabIndicatorHeight(Utils.dpToPx(2, this));
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void inflateData() {

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                //Log.e("AppBar","->Expanded");
                setActivityTitle("");
                mFavoriteButton.show();
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                //Log.e("AppBar","->Collapsed");
                setActivityTitle(movies.getTitle());
                mFavoriteButton.hide();
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
                //Log.e("AppBar","->Idle");
                //setActivityTitle("");
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        ImageLoadingUtils.load(mHeaderImage, "http://image.tmdb.org/t/p/w500/" + movies.getBackdrop_path());
        ImageLoadingUtils.load(mMoviePosterImage, "http://image.tmdb.org/t/p/w185/" + movies.getPoster_path());
        mMovieTitle.setText(movies.getTitle());
        mMovieGenre.setText(GenreHelper.getGenreNamesList(movies.getGenres()).trim());
        mFavoriteButton.setSelected(movies.isFavorite());

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(movies.isFavorite()) { // Already added is removed
                    LocalStoreUtil.removeFromFavorites(MovieDetailsActivity.this, movies.getId());
                    getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movies.getId())).build(), null, null);

                    ViewUtils.showToast(getResources().getString(R.string.removed_favorite),MovieDetailsActivity.this);
                    movies.setFavorite(false);

                } else {
                    LocalStoreUtil.addToFavorites(MovieDetailsActivity.this, movies.getId());
                    ContentValues values = MoviesOpenHelper.getMovieContentValues(movies);
                    getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, values);

                    ViewUtils.showToast(getResources().getString(R.string.added_favorite),MovieDetailsActivity.this);
                    movies.setFavorite(true);
                }

                isFavoriteChanged = true;
                mFavoriteButton.setSelected(movies.isFavorite());

                EventBus.getDefault().post(new FavoriteChangeEvent(isFavoriteChanged));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_share) {
            onClickShare();

        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickShare() {

        if(movieVideos!=null && !movieVideos.getKey().isEmpty()) {

            String message = movies.getOriginal_title() + "\nhttps://www.youtube.com/watch?v=" + movieVideos.getKey();
            ShareUtils.shareCustom(message, this);
        } else {
            Toast.makeText(this, "There is no video link to be shared", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final MovieVideosEvent event){
        Log.e("onEvent","->"+event.getMovieVideos());

        movieVideos = event.getMovieVideos();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        if(movies!=null)
            savedInstanceState.putParcelable(Movie.TAG_MOVIES, movies);

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Movie.TAG_MOVIES)) {
                movies = (Movie) savedInstanceState.getParcelable(Movie.TAG_MOVIES);
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }



}