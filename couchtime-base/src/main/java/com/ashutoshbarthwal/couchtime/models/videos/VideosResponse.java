package com.ashutoshbarthwal.couchtime.models.videos;

import com.ashutoshbarthwal.couchtime.models.movies.Movie;

import java.util.List;

/**
 * Created by Ashutosh on 04-03-2017.
 */

public class VideosResponse {
    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
