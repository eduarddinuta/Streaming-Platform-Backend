package factories;

import actions.ActionVisitor;
import actions.BuyTokensAction;
import actions.BuyPremiumAccountAction;
import actions.ChangePageAction;
import actions.FilterAction;
import actions.LikeAction;
import actions.LoginAction;
import actions.PurchaseAction;
import actions.RateAction;
import actions.RegisterAction;
import actions.SearchAction;
import actions.SeeDetailsAction;
import actions.WatchAction;


import input.ActionInput;

public final class ActionFactory {

    private static ActionFactory instance = null;

    private ActionFactory() {

    }

    /**
     * gets the singleton instance for ActionFactory
     * @return
     */
    public static ActionFactory getInstance() {
        if (instance == null) {
            instance = new ActionFactory();
        }

        return instance;
    }
    /**
     * Factory function to create every type of action needed on the platform.
     * Returns the specific action object based on the given input.
     * @param action
     * @return
     */
    public static ActionVisitor createAction(final ActionInput action) {

        switch (action.getType()) {
            case "change page":
                if (action.getMovie() == null) {
                    return new ChangePageAction(action.getPage());
                } else {
                    return new SeeDetailsAction(action.getMovie());
                }
            case "on page":
                switch (action.getFeature()) {
                    case "login": return new LoginAction(action.getCredentials());
                    case "register": return new RegisterAction(action.getCredentials());
                    case "search": return new SearchAction(action.getStartsWith());
                    case "filter": return new FilterAction(action.getFilters());
                    case "buy tokens": return new BuyTokensAction(action.getCount());
                    case "buy premium account": return new BuyPremiumAccountAction();
                    case "purchase": return new PurchaseAction();
                    case "watch": return new WatchAction();
                    case "like": return new LikeAction();
                    case "rate": return new RateAction(action.getRate());
                    default: throw new IllegalArgumentException("The action feature "
                            + action.getFeature() + " is not recognized.");
                }
            default:  throw new IllegalArgumentException("The action type "
                    + action.getType() + " is not recognized.");
        }
    }
}
