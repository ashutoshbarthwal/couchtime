package com.ashutoshbarthwal.couchtime.core.api;

import com.ashutoshbarthwal.couchtime.BuildConfig;
import com.ashutoshbarthwal.couchtime.models.movies.Movie;
import com.ashutoshbarthwal.couchtime.models.tmdb.TMDBResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public interface ApiModule {

    @GET(Endpoints.API_POPULAR_MOVIES)
    Call<TMDBResponse> getPopularMovies(
            @Query("api_key") String api_key,
            @Query("append_to_response") String includes,
            @Query("page") Integer page
    );

    @GET(Endpoints.API_UPCOMING_MOVIES)
    Call<TMDBResponse> getUpcomingMovies(
            @Query("api_key") String api_key,
            @Query("append_to_response") String includes,
            @Query("page") Integer page
    );

    @GET(Endpoints.API_TOP_RATED_MOVIES)
    Call<TMDBResponse> getTopRatedMovies(
            @Query("api_key") String api_key,
            @Query("append_to_response") String includes,
            @Query("page") Integer page
    );

    @GET(Endpoints.API_SINGLE_MOVIE)
    Call<Movie> getSingleMovie(
            @Path("id") Integer id,
            @Query("api_key") String api_key,
            @Query("append_to_response") String includes
    );
}
