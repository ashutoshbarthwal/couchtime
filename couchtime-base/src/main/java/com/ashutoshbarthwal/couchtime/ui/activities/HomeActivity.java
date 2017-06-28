package com.ashutoshbarthwal.couchtime.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ashutoshbarthwal.couchtime.R;
import com.ashutoshbarthwal.couchtime.core.BaseActivity;
import com.ashutoshbarthwal.couchtime.event.MovieSelectedEvent;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.models.sort.Sort;
import com.ashutoshbarthwal.couchtime.ui.fragments.FavouriteMoviesFragment;
import com.ashutoshbarthwal.couchtime.ui.fragments.MoviesDetailsFragment;
import com.ashutoshbarthwal.couchtime.ui.fragments.MoviesFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class HomeActivity extends BaseActivity{

    private final String TAG_SORT = "sort";
    private Sort mSort = Sort.POPULAR;

    private boolean mTwoPane;

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
        setContentView(R.layout.activity_home);

        showMoviesFragment();

        if (findViewById(R.id.homeDetailsFragment) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void showMoviesFragment() {
        replaceFragment(MoviesFragment.newInstance(mSort));
    }

    private void showFavoriteMoviesFragment() {
        replaceFragment(new FavouriteMoviesFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .commit();
    }

    private void replaceDetailsFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeDetailsFragment, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mSort) {
            case POPULAR:
                menu.findItem(R.id.sort_by_popularity).setChecked(true);
                break;
            case TOP_RATED:
                menu.findItem(R.id.sort_by_rating).setChecked(true);
                break;
            case FAVORITE:
                menu.findItem(R.id.sort_by_favorite).setChecked(true);
            case UPCOMING:
                menu.findItem(R.id.sort_by_favorite).setChecked(true);
                break;
        }
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.sort_by_popularity) {
            item.setChecked(!item.isChecked());
            onSortChanged(Sort.POPULAR);
            showMoviesFragment();

        } else if (i == R.id.sort_by_rating) {
            item.setChecked(!item.isChecked());
            onSortChanged(Sort.TOP_RATED);
            showMoviesFragment();

        } else if (i == R.id.sort_by_favorite) {
            item.setChecked(!item.isChecked());
            showFavoriteMoviesFragment();

        } else if (i == R.id.sort_by_upcoming) {
            item.setChecked(!item.isChecked());
            onSortChanged(Sort.UPCOMING);
            showMoviesFragment();

        }
        return super.onOptionsItemSelected(item);
    }

    private void onSortChanged(Sort sort) {
        mSort = sort;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        if(mSort!=null)
            savedInstanceState.putSerializable(TAG_SORT, mSort);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TAG_SORT)) {
                mSort = (Sort) savedInstanceState.getSerializable(TAG_SORT);
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final MovieSelectedEvent event){
        Log.e("onEvent","->"+event.getSelectedMovie());

        Movie movies = event.getSelectedMovie();

        if(movies!=null) {

            if (mTwoPane) {
                MoviesDetailsFragment fragment = MoviesDetailsFragment.newInstance(movies);
                replaceDetailsFragment(fragment);
            } else {
                Intent intent = new Intent(this, MovieDetailsActivity.class);
                intent.putExtra(Movie.TAG_MOVIES, movies);
                //startActivityForResult(intent, IntentRequestCodes.MOVIES_DETAILS_ACTIVITY);
                startActivity(intent);
            }
        }

    }
}