package users;

import input.CredentialsInput;
import movies.Movie;
import platform.PlatformGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

@JsonIgnoreProperties({"allowedMovies", "isPremium"})
public final class User {
    public static final int NUM_PREMIUM_MOVIES = 15;
    private CredentialsInput credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = NUM_PREMIUM_MOVIES;
    private ArrayList<Movie> purchasedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> watchedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> likedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> ratedMovies = new ArrayList<Movie>();

    private ArrayList<Movie> allowedMovies = new ArrayList<>();

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

}
