package com.ashutoshbarthwal.couchtime.models.reviews;

import com.ashutoshbarthwal.couchtime.models.movies.Movie;

import java.util.List;

/**
 * Created by Ashutosh on 04-03-2017.
 */

public class ReviewResponse {
    private int page;
    private int total_pages;
    private int total_results;
    /**
     * id : 5898288d9251417a85005c47
     * author : Gimly
     * content : I would never take it away from anyone, but I was underwhelmed.

     _Final rating:★★ - Definitely not for me, but I sort of get the appeal._
     * url : https://www.themoviedb.org/review/5898288d9251417a85005c47
     */

    private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
