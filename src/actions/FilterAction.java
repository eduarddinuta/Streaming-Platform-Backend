package actions;

import input.FilterInput;
import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.Collections;
import java.util.ArrayList;

public final class FilterAction extends ActionVisitor {

    private FilterInput filters;

    public FilterAction(final FilterInput filters) {
        this.filters = filters;
        actionName = "filter";
    }

    /**
     * Filters the current available movies by restricting them to contain specific actors or
     * genres and by sorting them by rating/duration.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {

        User currentUser = page.getUser();
        ArrayList<Movie> seenMovies = new ArrayList<Movie>();
        for (Movie movie: currentUser.getAllowedMovies()) {
            seenMovies.add(movie);
        }

        if (filters.getContains() != null) {

            ArrayList<Movie> toRemove = new ArrayList<>();
            for (Movie movie: seenMovies) {
                boolean hasActor = checkContains(filters.getContains().getActors(),
                        movie.getActors());
                boolean hasGenre = checkContains(filters.getContains().getGenre(),
                        movie.getGenres());
                if (!hasActor || !hasGenre) {
                    toRemove.add(movie);
                }
            }

            seenMovies.removeAll(toRemove);
        }

        if (filters.getSort() != null && !seenMovies.isEmpty()) {
            String ratingSort = filters.getSort().getRating();
            String durationSort = filters.getSort().getDuration();
            int ratingSign = setSortDirection(filters.getSort().getRating());
            int durationSign = setSortDirection(filters.getSort().getDuration());
            
            Collections.sort(seenMovies, (o1, o2) -> {
                if (durationSort != null && o1.getDuration() != o2.getDuration()) {
                    return durationSign * (o1.getDuration() - o2.getDuration());
                } else if (ratingSort != null) {
                    return ratingSign * Double.compare(o1.getRating(), o2.getRating());
                }
                return 0;
            });
        }

        ArrayList<Movie> outputMovies = new ArrayList<>();
        for (Movie movie: seenMovies) {
            outputMovies.add(new Movie(movie));
        }
        PlatformGenerator.getOutput().addPOJO(new Output(outputMovies, new User(page.getUser())));
        page.setSeenMovies(seenMovies);
    }

    /**
     * returns the value that will be used in the comparator to determine whether we sort
     * increasingly or decreasingly
     * @param direction
     * @return
     */
    private int setSortDirection(final String direction) {
        if (direction == null) {
            return 0;
        }

        if (direction.equals("decreasing")) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * checks if the current movie contains the desired elements
     * @param desiredElement
     * @param elements
     * @return
     */
    public boolean checkContains(final ArrayList<String> desiredElement,
                                 final ArrayList<String> elements) {
        if (desiredElement == null) {
            return true;
        }

        int count = 0;
        for (String actor: desiredElement) {
            if (elements.contains(actor)) {
                count += 1;
            }
        }

        return count == desiredElement.size();
    }

    public FilterInput getFilters() {
        return filters;
    }

    public void setFilters(final FilterInput filters) {
        this.filters = filters;
    }
}
