package actions;

import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

public final class BuyTokensAction extends ActionVisitor {
    private String tokenCount;

    public BuyTokensAction(final String tokenCount) {
        this.tokenCount = tokenCount;
        actionName = "buy tokens";
    }

    /**
     * Buys tokens using currency from the user's balance and adds them to
     * the token count. If the user does not have enough balance outputs an error.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {
        User currentUser = page.getUser();
        int tokens = Integer.valueOf(tokenCount);
        int balance = Integer.valueOf(currentUser.getCredentials().getBalance());
        if (balance >= tokens) {
            String newBalance = Integer.toString(balance - tokens);
            currentUser.getCredentials().setBalance(newBalance);
            currentUser.setTokensCount(currentUser.getTokensCount() + tokens);
            return;
        }

        PlatformGenerator.getOutput().addPOJO(new Output("Error"));
    }

    public String getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(final String tokenCount) {
        this.tokenCount = tokenCount;
    }
}
