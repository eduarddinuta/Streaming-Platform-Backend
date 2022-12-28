package pages;

import movies.Movie;

public final class ChangedPage {
    private String page;
    private Movie movie;

    public ChangedPage(final String page, final Movie movie) {
        this.page = page;
        this.movie = movie;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }
}
