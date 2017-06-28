package com.ashutoshbarthwal.couchtime.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.ashutoshbarthwal.couchtime";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class MoviesEntry implements BaseColumns {

        //table movies
        public static final String TABLE_MOVIES = "MOVIES";

        //columns

        public static final String ID = "id";
        public static final String MOVIE_ID = "tmdb_movie_id";
        public static final String MOVIE_ADULT = "is_adult";
        public static final String MOVIE_POSTER_PATH = "poster_path";
        public static final String MOVIE_OVERVIEW = "movie_overview";
        public static final String MOVIE_GENRES = "genres";
        public static final String MOVIE_RELEASE_DATE = "release_date";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_ORIGINAL_TITLE = "original_title";
        public static final String MOVIE_LANGUAGE = "language";
        public static final String MOVIE_BACKDROP_PATH = "backdrop_path";
        public static final String MOVIE_POPULARITY = "popularity";
        public static final String MOVIE_VIDEO = "video";
        public static final String MOVIE_VOTE_AVERAGE = "vote_average";
        public static final String MOVIE_VOTE_COUNT = "vote_count";

        //create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIES).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        // for building URIs on insertion
        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
