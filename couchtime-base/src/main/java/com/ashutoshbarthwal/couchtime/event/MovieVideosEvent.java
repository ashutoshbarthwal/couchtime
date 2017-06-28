package com.ashutoshbarthwal.couchtime.event;

import com.ashutoshbarthwal.couchtime.models.videos.Video;

/**
 * Created by Ashutosh on 03-03-2017.
 */
public class MovieVideosEvent {

    public final Video movieVideos;

    public MovieVideosEvent(Video movieVideos) {
        this.movieVideos = movieVideos;
    }

    public Video getMovieVideos() {
        return movieVideos;
    }

}
