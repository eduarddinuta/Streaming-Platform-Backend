package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;

public class SubscribeAction extends ActionVisitor{
    String subscribedGenre;

    public SubscribeAction(String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
        actionName = "subscribe";
    }

    @Override
    public void visit(ConcretePage page) {
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
}
