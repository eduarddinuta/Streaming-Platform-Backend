package pages;

import actions.ActionVisitor;

public interface Page {
    /**
     * accept function for the visitor pattern
     * @param action
     */
    void accept(ActionVisitor action);
}
