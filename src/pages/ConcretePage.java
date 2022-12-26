package pages;

import actions.ActionVisitor;
import movies.Movie;
import users.User;

import java.util.ArrayList;

public final class ConcretePage implements Page {
    private String name;
    private ArrayList<String> allowedActions;
    private ArrayList<String> allowedPages;

    private ArrayList<Movie> seenMovies;
    private User user;
    public ConcretePage(final String name, final User user,
                        final ArrayList<String> allowedActions,
                        final ArrayList<String> allowedPages) {
        this.name = name;
        this.user = user;
        this.allowedActions = allowedActions;
        this.allowedPages = allowedPages;
    }

    /**
     * Accept function for implementing the visitor pattern for pages and actions.
     * Outputs an error if the action tried is not permitted on the current page.
     * @param action
     */
    @Override
    public void accept(final ActionVisitor action) {
        action.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getAllowedActions() {
        return allowedActions;
    }

    public void setAllowedActions(final ArrayList<String> allowedActions) {
        this.allowedActions = allowedActions;
    }

    public ArrayList<String> getAllowedPages() {
        return allowedPages;
    }

    public void setAllowedPages(final ArrayList<String> allowedPages) {
        this.allowedPages = allowedPages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public ArrayList<Movie> getSeenMovies() {
        return seenMovies;
    }

    public void setSeenMovies(final ArrayList<Movie> seenMovies) {
        this.seenMovies = seenMovies;
    }
}
