package actions;


import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public final class RecommendAction extends ActionVisitor {

    public RecommendAction() {
        actionName = "recommend";
    }

    /**
     * Searches for a movie recommendation for the currently logged
     * premium user. Number of likes for every genre is stored in a
     * TreeMap, then all the available movies are sorted by decreasing
     * by likes. We search a movie not yet seen by the user from every
     * genre, starting with the genre with most likes, then we move to the
     * next one. Sends a notification to the user with the movie name if it
     * has been found or "No recommendation" if no movie was found
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();
        if (currentUser == null
                || currentUser.getCredentials().getAccountType().equals("standard")) {
            return;
        }

        TreeMap<String, Integer> likedGenres = new TreeMap<>();
        for (Movie movie: currentUser.getLikedMovies()) {
            for (String genre: movie.getGenres()) {
                int nrLikes = 0;
                if (likedGenres.get(genre) != null) {
                    nrLikes = likedGenres.get(genre);
                }
                likedGenres.put(genre, nrLikes + 1);
            }
        }

        ArrayList<Movie> visibleMovies = currentUser.getAllowedMovies();
        Collections.sort(visibleMovies, Comparator.comparingInt(Movie::getNumLikes).reversed());

        int size = likedGenres.size();
        for (int i = 0; i < size; i++) {
            String mostLiked = getMax(likedGenres);
            for (Movie movie: visibleMovies) {
                if (movie.getGenres().contains(mostLiked)
                        && !currentUser.getWatchedMovies().contains(movie)
                        && !movie.getCountriesBanned().
                        contains(currentUser.getCredentials().getCountry())) {
                    currentUser.update(movie, "Recommendation");
                    Output output = new Output(new User(currentUser));
                    output.setCurrentMoviesList(null);
                    PlatformGenerator.getOutput().addPOJO(output);
                    return;
                }
            }

            likedGenres.put(mostLiked, 0);
        }

        currentUser.update(new Movie("No recommendation", 0, 0,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), "Recommendation");

        Output output = new Output(new User(currentUser));
        output.setCurrentMoviesList(null);
        PlatformGenerator.getOutput().addPOJO(output);
    }

    /**
     * Calculates the genre with the most likes at every step in the algorithm
     * @param likedGenres
     * @return returns the genre with most likes from the TreeMap
     */
    public String getMax(final TreeMap<String, Integer> likedGenres) {
        int max = 0;
        String genre = null;
        for (Map.Entry<String, Integer> entry: likedGenres.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                genre = entry.getKey();
            }
        }

        return genre;
    }
}
