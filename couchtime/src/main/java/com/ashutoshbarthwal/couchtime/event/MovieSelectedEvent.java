package com.ashutoshbarthwal.couchtime.event;


import com.ashutoshbarthwal.couchtime.models.movies.Movie;

/**
 * Created by Ashutosh on 03-03-2017.
 */
public class MovieSelectedEvent {

    public final Movie movies;

    public MovieSelectedEvent(Movie movies) {
        this.movies = movies;
    }

    public Movie getSelectedMovie() {
        return movies;
    }

}
