package actions;

import movies.Movie;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class PurchaseAction extends ActionVisitor {

    public PurchaseAction() {
        actionName = "purchase";
    }

    /**
     * Adds a movie in the current user's purchased section. Substracts 2 from the
     * users token count or 1 from the number of premium movies if the account is premium
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {

        User currentUser = page.getUser();
        String accountType = currentUser.getCredentials().getAccountType();
        Movie purchasedMovie = page.getSeenMovies().get(0);

        if (currentUser.getPurchasedMovies().contains(purchasedMovie)) {
            PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            return;
        }

        if (accountType.equals("premium") && currentUser.getNumFreePremiumMovies() > 0) {
            currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
        } else {
            if (currentUser.getTokensCount() >= 2) {
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
            } else {
                PlatformGenerator.getOutput().addPOJO(new Output("Error"));
                return;
            }
        }

        currentUser.getPurchasedMovies().add(purchasedMovie);
        ArrayList<Movie> outputMovies = new ArrayList<>();
        outputMovies.add(new Movie(purchasedMovie));
        PlatformGenerator.getOutput().addPOJO(new Output(outputMovies, new User(page.getUser())));
    }
}
