package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class RateAction extends ActionVisitor {
    private Double rating;
    public static final int MAX_RATING = 5;
    public RateAction(final Double rating) {
        this.rating = rating;
        actionName = "rate";
    }

    /**
     * Adds a movie to the current user's rated section. Increments the number of ratings of
     * the movie and modifies the average rating. If the movie was not watched before outputs an
     * error.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();
        Movie currentMovie = page.getSeenMovies().get(0);
        ArrayList<Movie> watchedMovies = currentUser.getWatchedMovies();

        if (!watchedMovies.contains(currentMovie) || rating > MAX_RATING || rating < 1) {
            PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            return;
        }

        if (!currentUser.getRatedMovies().contains(currentMovie)) {
            currentMovie.setNumRatings(currentMovie.getNumRatings() + 1);
            currentMovie.getRatings().add(rating);
            currentUser.getGivenRatings().put(currentMovie, rating);
            currentUser.getRatedMovies().add(currentMovie);
        } else {
            currentMovie.getRatings().remove(currentUser.getGivenRatings().get(currentMovie));
            currentMovie.getRatings().add(rating);
            currentUser.getGivenRatings().put(currentMovie, rating);
        }
        currentMovie.calculateAverageRating();

        ArrayList<Movie> outputMovies = new ArrayList<>();
        outputMovies.add(new Movie(currentMovie));

        PlatformGenerator.getOutput().addPOJO(new Output(outputMovies, new User(currentUser)));
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }
}
