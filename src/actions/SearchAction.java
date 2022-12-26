package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class SearchAction extends ActionVisitor {
    private String searchedText;

    public SearchAction(final String searchedText) {
        this.searchedText = searchedText;
        actionName = "search";
    }

    /**
     * Modifies the currently seen movies by keeping only those starting with
     * searchedText.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();

        ArrayList<Movie> allowedMovies = currentUser.getAllowedMovies();
        ArrayList<Movie> seenMovies = new ArrayList<Movie>();

        for (Movie movie: allowedMovies) {
            if (movie.getName().startsWith(searchedText)) {
                seenMovies.add(movie);
            }
        }

        ArrayList<Movie> outputMovies = new ArrayList<>();
        for (Movie movie: seenMovies) {
            outputMovies.add(new Movie(movie));
        }
        PlatformGenerator.getOutput().addPOJO(new Output(outputMovies, new User(page.getUser())));
        page.setSeenMovies(new ArrayList<Movie>(seenMovies));
    }

    public String getSearchedText() {
        return searchedText;
    }

    public void setSearchedText(final String searchedText) {
        this.searchedText = searchedText;
    }
}
