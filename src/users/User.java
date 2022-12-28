package users;

import input.CredentialsInput;
import movies.Movie;
import platform.PlatformGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties({"allowedMovies", "isPremium", "subscribedGenres", "givenRatings"})
public final class User implements ObserverObject{
    public static final int NUM_PREMIUM_MOVIES = 15;
    private CredentialsInput credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = NUM_PREMIUM_MOVIES;
    private ArrayList<Movie> purchasedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> watchedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> likedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> ratedMovies = new ArrayList<Movie>();

    private ArrayList<Movie> allowedMovies = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();
    private ArrayList<String> subscribedGenres = new ArrayList<>();
    private HashMap<Movie, Double> givenRatings = new HashMap<>();
    public User(final CredentialsInput credentials) {
        this.credentials = new CredentialsInput(credentials);

        for (Movie movie: PlatformGenerator.getMovies()) {
            boolean isBanned = false;
            for (String country: movie.getCountriesBanned()) {
                if (country.equals(credentials.getCountry())) {
                    isBanned = true;
                }
            }

            if (!isBanned) {
                allowedMovies.add(movie);
            }
        }
    }

    public User(final User user) {
        this.credentials = new CredentialsInput(user.getCredentials());
        this.tokensCount = user.getTokensCount();
        this.numFreePremiumMovies = user.getNumFreePremiumMovies();

        this.watchedMovies = new ArrayList<Movie>();
        for (Movie movie : user.getWatchedMovies()) {
            this.watchedMovies.add(new Movie(movie));
        }

        this.purchasedMovies = new ArrayList<Movie>();
        for (Movie movie : user.getPurchasedMovies()) {
            this.purchasedMovies.add(new Movie(movie));
        }

        this.likedMovies = new ArrayList<Movie>();
        for (Movie movie : user.getLikedMovies()) {
            this.likedMovies.add(new Movie(movie));
        }

        this.ratedMovies = new ArrayList<Movie>();
        for (Movie movie : user.getRatedMovies()) {
            this.ratedMovies.add(new Movie(movie));
        }

        this.allowedMovies = new ArrayList<Movie>();
        for (Movie movie : user.getAllowedMovies()) {
            this.allowedMovies.add(new Movie(movie));
        }

        this.notifications = new ArrayList<>();
        for (Notification notification: user.getNotifications()) {
            this.notifications.add(new Notification(notification));
        }

        this.subscribedGenres = new ArrayList<>();
        for (String genre: user.getSubscribedGenres()) {
            this.subscribedGenres.add(genre);
        }

        this.givenRatings = new HashMap<>();
        for (Map.Entry<Movie, Double> entry: givenRatings.entrySet()) {
            this.givenRatings.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void update(Movie newMovie, String message) {
        if (newMovie.getName().equals("No recommendation")) {
            notifications.add(new Notification(newMovie.getName(), message));
            return;
        }

        boolean isBanned = false;
        for (String country: newMovie.getCountriesBanned()) {
            if (country.equals(credentials.getCountry())) {
                isBanned = true;
            }
        }

        if (isBanned) {
            return;
        }

        allowedMovies.add(newMovie);
        ArrayList<String> genres = newMovie.getGenres();
        for (String genre: genres) {
            if (subscribedGenres.contains(genre) || message.equals("Recommendation")) {
                notifications.add(new Notification(newMovie.getName(), message));
                return;
            }
        }
    }

    public HashMap<Movie, Double> getGivenRatings() {
        return givenRatings;
    }

    public void setGivenRatings(HashMap<Movie, Double> givenRatings) {
        this.givenRatings = givenRatings;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public void setSubscribedGenres(ArrayList<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = new CredentialsInput(credentials);
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<Movie> getAllowedMovies() {
        return allowedMovies;
    }

    public void setAllowedMovies(final ArrayList<Movie> allowedMovies) {
        this.allowedMovies = allowedMovies;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

}
