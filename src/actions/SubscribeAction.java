package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;

public final class SubscribeAction extends ActionVisitor {
    private String subscribedGenre;

    public SubscribeAction(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
        actionName = "subscribe";
    }

    /**
     * Subscribes the current user to a genre, or outputs
     * an error if the user is already subscribed or the current
     * movie doesn't have the desired genre
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        Movie movie = page.getSeenMovies().get(0);
        if (page.getUser().getSubscribedGenres().contains(subscribedGenre)) {
            PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            return;
        }

        if (movie.getGenres().contains(subscribedGenre)) {
            page.getUser().getSubscribedGenres().add(subscribedGenre);
            return;
        }

        PlatformGenerator.getOutput().addPOJO(new Output("Error"));
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }
}
