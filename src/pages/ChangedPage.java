package pages;

import movies.Movie;

public class ChangedPage {
    private String page;
    private Movie movie;

    public ChangedPage(String page, Movie movie) {
        this.page = page;
        this.movie = movie;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
