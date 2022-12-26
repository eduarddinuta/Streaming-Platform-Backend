package actions;

import pages.ConcretePage;

public abstract class ActionVisitor {

    protected String actionName;

    /**
     * Visit function used for implementing visitor pattern for pages and actions.
     * implemented by every action type.
     * @param page
     */
    public abstract void visit(ConcretePage page);

    /**
     * Gets the action name
     * @return
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * Sets the action name
     * @param actionName
     */
    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }
}
