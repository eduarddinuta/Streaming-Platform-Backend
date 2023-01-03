package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class WatchAction extends ActionVisitor {

    public WatchAction() {
        actionName = "watch";
    }

    /**
     * Adds the movie to the current user's watched section. If the movie was not
     * purchased before outputs an error.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();
        Movie currentMovie = page.getSeenMovies().get(0);
        ArrayList<Movie> purchasedMovies = currentUser.getPurchasedMovies();

        if (!purchasedMovies.contains(currentMovie)) {
            PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            return;
        }

        if (!currentUser.getWatchedMovies().contains(currentMovie)) {
            currentUser.getWatchedMovies().add(currentMovie);
        }

        ArrayList<Movie> outputMovies = new ArrayList<>();
        outputMovies.add(new Movie(currentMovie));
        PlatformGenerator.getOutput().addPOJO(new Output(outputMovies, new User(page.getUser())));
    }
}
