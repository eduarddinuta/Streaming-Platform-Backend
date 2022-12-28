package users;

import movies.Movie;

public interface ObserverObject {
    /**
     * Update function implemented by the user observers
     * @param movie
     * @param message
     */
    void update(Movie movie, String message);
}
