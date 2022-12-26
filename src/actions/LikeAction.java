package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class LikeAction extends ActionVisitor {

    public LikeAction() {
        actionName = "like";
    }

    /**
     * Adds a movie to the current user's liked section and increments the numver of likes
     * on the movie. If the movie was not watched before outputs an error.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();
        Movie currentMovie = page.getSeenMovies().get(0);
        ArrayList<Movie> watchedMovies = currentUser.getWatchedMovies();

        if (!watchedMovies.contains(currentMovie)) {
            PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            return;
        }

        currentMovie.setNumLikes(currentMovie.getNumLikes() + 1);
        currentUser.getLikedMovies().add(currentMovie);
        ArrayList<Movie> outputMovies = new ArrayList<>();
        outputMovies.add(new Movie(currentMovie));
        PlatformGenerator.getOutput().addPOJO(new Output(outputMovies, new User(currentUser)));
    }
}
