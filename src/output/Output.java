package output;

import movies.Movie;
import users.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({"error", "currentMoviesList", "currentUser"})
public final class Output {
    private String error = null;
    private ArrayList<Movie> currentMoviesList = new ArrayList<>();
    private User currentUser = null;

    public Output() {

    }

    public Output(final ArrayList<Movie> currentMoviesList, final User currentUser) {
        this.currentUser = currentUser;
        this.currentMoviesList = currentMoviesList;
    }
    public Output(final String error) {
        this.error = error;
    }

    public Output(final User currentUser) {
        this.currentUser = currentUser;
    }

    public Output(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }
    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }
}
