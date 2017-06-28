package com.ashutoshbarthwal.couchtime.core.api;

import com.ashutoshbarthwal.couchtime.BuildConfig;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class Endpoints {
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_POPULAR_MOVIES = "movie/popular";
    public static final String API_UPCOMING_MOVIES = "movie/upcoming";
    public static final String API_TOP_RATED_MOVIES = "movie/top_rated";
    public static final String API_SINGLE_MOVIE = "movie/{id}";
    public static final String TMDB_API_KEY=BuildConfig.TMDB_API_KEY;
}
