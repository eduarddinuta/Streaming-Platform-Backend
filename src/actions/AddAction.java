package actions;

import input.MovieInput;
import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;

import java.util.ArrayList;

public class AddAction extends  ActionVisitor{

    private Movie addedMovie;

    public AddAction(MovieInput movie) {
        this.addedMovie = new Movie(new Movie(movie.getName(), movie.getYear(),
                movie.getDuration(), movie.getGenres(),
                movie.getActors(), movie.getCountriesBanned()));
        actionName = "add";
    }

    @Override
    public void visit(ConcretePage page) {
        ArrayList<Movie> movies = PlatformGenerator.getMovies();
        for (Movie movie: movies) {
            if (movie.getName().equals(addedMovie.getName())) {
                PlatformGenerator.getOutput().addPOJO(new Output("Error"));
                return;
            }
        }
        movies.add(addedMovie);
    }
}
