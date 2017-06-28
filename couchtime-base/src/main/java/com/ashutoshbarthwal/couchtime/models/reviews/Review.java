package com.ashutoshbarthwal.couchtime.models.reviews;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class Review {

    /**
     * id : 5898288d9251417a85005c47
     * author : Gimly
     * content : I would never take it away from anyone, but I was underwhelmed.

     _Final rating:★★ - Definitely not for me, but I sort of get the appeal._
     * url : https://www.themoviedb.org/review/5898288d9251417a85005c47
     */

    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
