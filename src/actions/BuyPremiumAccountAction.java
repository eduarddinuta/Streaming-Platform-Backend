package actions;

import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

public final class BuyPremiumAccountAction extends ActionVisitor {
    public static final int PREMIUM_PRICE = 10;
    public BuyPremiumAccountAction() {
        actionName = "buy premium account";
    }

    /**
     * Sets current user's account type to premium if the user has enough tokens
     * to pay for it, else outputs an error
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();
        if (currentUser.getTokensCount() >= PREMIUM_PRICE) {
            currentUser.setTokensCount(currentUser.getTokensCount() - PREMIUM_PRICE);
            currentUser.getCredentials().setAccountType("premium");

            return;
        }

        PlatformGenerator.getOutput().addPOJO(new Output("Error"));
    }
}
