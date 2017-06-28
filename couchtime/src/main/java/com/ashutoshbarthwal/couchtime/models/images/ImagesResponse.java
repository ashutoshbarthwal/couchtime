package com.ashutoshbarthwal.couchtime.models.images;

import java.util.List;

/**
 * Created by Ashutosh on 04-03-2017.
 */

public class ImagesResponse {
    /**
     * aspect_ratio : 1.77702702702703
     * file_path : /6vkhRvsRvWpmaRVyCXaxTkIEb7j.jpg
     * height : 1628
     * iso_639_1 : null
     * vote_average : 5.384
     * vote_count : 2
     * width : 2893
     */

    private List<Image> backdrops;
    /**
     * aspect_ratio : 0.666666666666667
     * file_path : /xq1Ugd62d23K2knRUx6xxuALTZB.jpg
     * height : 3000
     * iso_639_1 : en
     * vote_average : 5.522
     * vote_count : 4
     * width : 2000
     */

    private List<Image> posters;

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Image> getPosters() {
        return posters;
    }

    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }
}
