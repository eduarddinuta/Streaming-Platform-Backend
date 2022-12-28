package actions;

import factories.ActionFactory;
import input.ActionInput;
import output.Output;
import pages.ChangedPage;
import pages.ConcretePage;
import platform.PlatformGenerator;

import java.util.ArrayList;

public final class BackAction extends ActionVisitor {

    public BackAction() {
        actionName = "back";
    }

    @Override
    public void visit(final ConcretePage page) {
        ArrayList<ChangedPage> pageStack = PlatformGenerator.getChangedPages();
        if (pageStack.size() == 0) {
            PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            return;
        }

        ChangedPage previous = pageStack.get(pageStack.size() - 1);
        pageStack.remove(pageStack.size() - 1);
        ActionInput action = new ActionInput();
        action.setType("change page");
        action.setPage(previous.getPage());
        action.setBack(Boolean.TRUE);
        if (previous.getPage().equals("see details")) {
            action.setMovie(previous.getMovie().getName());
        }
        ActionVisitor actionVisitor = ActionFactory.getInstance().createAction(action);
        page.accept(actionVisitor);
    }
}
