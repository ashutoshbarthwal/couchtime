package com.ashutoshbarthwal.couchtime.models.movies;

import android.os.Parcel;
import android.os.Parcelable;

import com.ashutoshbarthwal.couchtime.models.Genre;
import com.ashutoshbarthwal.couchtime.models.images.Image;
import com.ashutoshbarthwal.couchtime.models.reviews.ReviewResponse;
import com.ashutoshbarthwal.couchtime.models.videos.VideosResponse;

import java.util.List;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class Movie implements Parcelable{

    public static final String TAG_MOVIES = "movies";
    private boolean adult,favorite;
    private String backdrop_path;
    private Object belongs_to_collection;
    private int budget;
    private String homepage;
    private int id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private int revenue;
    private int runtime;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
    /**
     * page : 1
     * results : [{"id":"5898288d9251417a85005c47","author":"Gimly","content":"I would never take it away from anyone, but I was underwhelmed.\r\n\r\n_Final rating:★★ - Definitely not for me, but I sort of get the appeal._","url":"https://www.themoviedb.org/review/5898288d9251417a85005c47"},{"id":"58afc9c1c3a3682cd0007c1a","author":"BecauseImBatman","content":"It has beautiful animation and beautiful characters. It is a funny, sweet and emotional roller coaster of a crowd-pleaser that manages to win your heart.","url":"https://www.themoviedb.org/review/58afc9c1c3a3682cd0007c1a"}]
     * total_pages : 1
     * total_results : 2
     */

    private ReviewResponse reviews;
    private VideosResponse videos;
    private ImagesBean images;
    /**
     * id : 16
     * name : Animation
     */

    private List<Genre> genres;
    /**
     * name : Toho Company
     * id : 882
     */

    private List<ProductionCompaniesBean> production_companies;
    /**
     * iso_3166_1 : JP
     * name : Japan
     */

    private List<ProductionCountriesBean> production_countries;
    /**
     * iso_639_1 : ja
     * name : 日本語
     */

    private List<SpokenLanguagesBean> spoken_languages;

    public Movie( int id,
                   boolean adult,
                   String poster_path,
                   String overview,
                   String release_date,
                   List<Integer> genre_ids,
                   String original_title,
                   String original_language,
                   String title,
                   String backdrop_path,
                   String popularity,
                   boolean video,
                   String vote_average,
                   int vote_count){

        this.id = id;
        this.adult = adult;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = Double.parseDouble(popularity);
        this.video = video;
        this.vote_average = Double.parseDouble(vote_average);
        this.vote_count = vote_count;

    }

    protected Movie(Parcel in) {
        adult = in.readByte() != 0;
        favorite = in.readByte() != 0;
        backdrop_path = in.readString();
        budget = in.readInt();
        homepage = in.readString();
        id = in.readInt();
        imdb_id = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        release_date = in.readString();
        revenue = in.readInt();
        runtime = in.readInt();
        status = in.readString();
        tagline = in.readString();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        vote_count = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Object getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public void setBelongs_to_collection(Object belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<ProductionCompaniesBean> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompaniesBean> production_companies) {
        this.production_companies = production_companies;
    }

    public List<ProductionCountriesBean> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<ProductionCountriesBean> production_countries) {
        this.production_countries = production_countries;
    }

    public List<SpokenLanguagesBean> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<SpokenLanguagesBean> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeByte((byte) (favorite ? 1 : 0));
        parcel.writeString(backdrop_path);
        parcel.writeInt(budget);
        parcel.writeString(homepage);
        parcel.writeInt(id);
        parcel.writeString(imdb_id);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeString(overview);
        parcel.writeDouble(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeInt(revenue);
        parcel.writeInt(runtime);
        parcel.writeString(status);
        parcel.writeString(tagline);
        parcel.writeString(title);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeDouble(vote_average);
        parcel.writeInt(vote_count);
    }

    public ReviewResponse getReviews() {
        return reviews;
    }

    public void setReviews(ReviewResponse reviews) {
        this.reviews = reviews;
    }

    public VideosResponse getVideos() {
        return videos;
    }

    public void setVideos(VideosResponse videos) {
        this.videos = videos;
    }


    public static class ImagesBean {
        /**
         * aspect_ratio : 1.77702702702703
         * file_path : /6vkhRvsRvWpmaRVyCXaxTkIEb7j.jpg
         * height : 1628
         * iso_639_1 : null
         * vote_average : 5.384
         * vote_count : 2
         * width : 2893
         */

        private List<BackdropsBean> backdrops;
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

        public List<BackdropsBean> getBackdrops() {
            return backdrops;
        }

        public void setBackdrops(List<BackdropsBean> backdrops) {
            this.backdrops = backdrops;
        }

        public List<Image> getPosters() {
            return posters;
        }

        public void setPosters(List<Image> posters) {
            this.posters = posters;
        }

        public static class BackdropsBean {
            private double aspect_ratio;
            private String file_path;
            private int height;
            private Object iso_639_1;
            private double vote_average;
            private int vote_count;
            private int width;

            public double getAspect_ratio() {
                return aspect_ratio;
            }

            public void setAspect_ratio(double aspect_ratio) {
                this.aspect_ratio = aspect_ratio;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public Object getIso_639_1() {
                return iso_639_1;
            }

            public void setIso_639_1(Object iso_639_1) {
                this.iso_639_1 = iso_639_1;
            }

            public double getVote_average() {
                return vote_average;
            }

            public void setVote_average(double vote_average) {
                this.vote_average = vote_average;
            }

            public int getVote_count() {
                return vote_count;
            }

            public void setVote_count(int vote_count) {
                this.vote_count = vote_count;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }

    }

    public static class ProductionCompaniesBean {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ProductionCountriesBean {
        private String iso_3166_1;
        private String name;

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SpokenLanguagesBean {
        private String iso_639_1;
        private String name;

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
