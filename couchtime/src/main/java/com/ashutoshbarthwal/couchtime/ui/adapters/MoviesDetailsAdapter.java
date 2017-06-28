package com.ashutoshbarthwal.couchtime.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.ui.fragments.OverviewFragment;
import com.ashutoshbarthwal.couchtime.ui.fragments.ReviewsFragment;
import com.ashutoshbarthwal.couchtime.ui.fragments.VideosFragment;
import com.ashutoshbarthwal.couchtime.widgets.FragmentStatePagerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Ashutosh on 04-03-2017.
 */
public class MoviesDetailsAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Overview", "Videos", "Reviews"};
    private FragmentManager fm;
    private Movie movies;

    public MoviesDetailsAdapter(FragmentManager fm, Movie movies) {
        super(fm);
        this.fm=fm;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Log.e("MOVIES",gson.toJson(movies));
        switch (position) {
            case 0:
                return OverviewFragment.newInstance(movies);
            case 1:
                return VideosFragment.newInstance(movies);
            case 2:
                return ReviewsFragment.newInstance(movies);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
