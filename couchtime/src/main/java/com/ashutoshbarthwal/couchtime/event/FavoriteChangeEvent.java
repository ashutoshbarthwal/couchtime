package com.ashutoshbarthwal.couchtime.event;

/**
 * Created by Ashutosh on 03-03-2017.
 */
public class FavoriteChangeEvent {

    public final boolean isFavorite;

    public FavoriteChangeEvent(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavoriteChanged() {
        return isFavorite;
    }

}
