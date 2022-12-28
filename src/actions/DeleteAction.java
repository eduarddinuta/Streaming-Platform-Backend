package actions;

import input.MovieInput;
import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public class DeleteAction extends ActionVisitor{
    private String removedMovie;

    public DeleteAction(String movie) {
        this.removedMovie = movie;
        actionName = "delete";
    }

    @Override
    public void visit(ConcretePage page) {
        ArrayList<Movie> movies = PlatformGenerator.getMovies();
        for (Movie movie: movies) {
            if (movie.getName().equals(removedMovie)) {
                movies.remove(movie);
                for (User user: PlatformGenerator.getUsers())
                    user.update(movie, "DELETE");
                return;
            }
        }
        PlatformGenerator.getOutput().addPOJO(new Output("Error"));
    }
}
