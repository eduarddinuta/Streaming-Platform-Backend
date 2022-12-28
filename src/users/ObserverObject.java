package users;

import movies.Movie;

public interface ObserverObject {
    void update(Movie movie, String message);
}
