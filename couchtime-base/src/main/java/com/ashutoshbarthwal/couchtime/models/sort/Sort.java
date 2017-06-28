package com.ashutoshbarthwal.couchtime.models.sort;

/**
 * Created by Ashutosh on 04-03-2017.
 */

public enum Sort {

    POPULAR("popular"),
    TOP_RATED("top_rated"),
    FAVORITE("favorite"),
    UPCOMING("upcoming");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @Override public String toString() {
        return value;
    }
}
