package actions;

import com.sun.source.tree.Tree;
import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.security.KeyPair;
import java.util.*;

public class RecommendAction extends ActionVisitor{

    public RecommendAction() {
        actionName = "recommend";
    }

    @Override
    public void visit(ConcretePage page) {
        User currentUser = page.getUser();
        if (currentUser == null
                || currentUser.getCredentials().getAccountType().equals("standard")) {
            return;
        }

        TreeMap<String, Integer> likedGenres = new TreeMap<>();
        for (Movie movie: currentUser.getLikedMovies()) {
            for (String genre: movie.getGenres()) {
                int nrLikes = 0;
                if (likedGenres.get(genre) != null)
                    nrLikes = likedGenres.get(genre);
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
                new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>()), "Recommendation");

        Output output = new Output(new User(currentUser));
        output.setCurrentMoviesList(null);
        PlatformGenerator.getOutput().addPOJO(output);
    }

    public String getMax(TreeMap<String, Integer> likedGenres) {
        int Max = 0;
        String genre = null;
        for (Map.Entry<String, Integer> entry: likedGenres.entrySet()) {
            if (entry.getValue() > Max) {
                Max = entry.getValue();
                genre = entry.getKey();
            }
        }

        return genre;
    }
}
