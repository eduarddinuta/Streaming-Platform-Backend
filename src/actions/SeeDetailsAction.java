package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class SeeDetailsAction extends ActionVisitor {
    private String movieName;

    public SeeDetailsAction(final String movieName) {
        this.movieName = movieName;
        actionName = "change page";
    }

    /**
     * Changes the currently seen movie to only one movie (movieName) and
     * outputs its details.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        ArrayList<Movie> seenMovies = page.getSeenMovies();

        for (Movie movie: seenMovies) {
            if (movieName.equals(movie.getName())) {
                page.setName("see details");
                page.setAllowedPages(PlatformGenerator.getAllowedPagesTable().get("see details"));
                page.setAllowedActions(PlatformGenerator.getAllowedActionsTable()
                        .get("see details"));

                ArrayList<Movie> newSeenMovies = new ArrayList<>();
                newSeenMovies.add(movie);
                page.setSeenMovies(newSeenMovies);

                ArrayList<Movie> outputMovies = new ArrayList<>();
                outputMovies.add(new Movie(movie));
                PlatformGenerator.getOutput().addPOJO(new Output(outputMovies,
                        new User(page.getUser())));
                return;
            }
        }

        PlatformGenerator.getOutput().addPOJO(new Output("Error"));
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
}
