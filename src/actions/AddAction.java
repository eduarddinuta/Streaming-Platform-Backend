package actions;

import input.MovieInput;
import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class AddAction extends  ActionVisitor {

    private Movie addedMovie;

    public AddAction(final MovieInput movie) {
        this.addedMovie = new Movie(new Movie(movie.getName(), movie.getYear(),
                movie.getDuration(), movie.getGenres(),
                movie.getActors(), movie.getCountriesBanned()));
        actionName = "add";
    }

    @Override
    public void visit(final ConcretePage page) {
        ArrayList<Movie> movies = PlatformGenerator.getMovies();
        for (Movie movie: movies) {
            if (movie.getName().equals(addedMovie.getName())) {
                PlatformGenerator.getOutput().addPOJO(new Output("Error"));
                return;
            }
        }
        movies.add(addedMovie);
        for (User user: PlatformGenerator.getUsers()) {
            if (!addedMovie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                user.update(addedMovie, "ADD");
                user.getAllowedMovies().add(addedMovie);
            }
        }
    }
}
