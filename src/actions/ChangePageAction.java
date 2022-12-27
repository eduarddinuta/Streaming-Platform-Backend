package actions;

import movies.Movie;
import output.Output;
import pages.ChangedPage;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

import java.util.ArrayList;

public final class ChangePageAction extends ActionVisitor {
    private String pageName;
    public ChangePageAction(final String pageName) {
        this.pageName = pageName;
        actionName = "change page";
    }

    /**
     * Changes the current page to pageName if pageName can be reached directly from
     * the current page, else outputs an error and the current page remains unchanged.
     * If pageName is movies then it outputs all the available movies.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {

        if (page.getAllowedPages().contains(pageName)) {
            if (pageName.equals("logout")) {
                PlatformGenerator.setChangedPages(new ArrayList<>());
                page.setName("homepage neautentificat");
                page.setUser(null);
                page.setAllowedPages(PlatformGenerator.getAllowedPagesTable()
                        .get("homepage neautentificat"));
                page.setAllowedActions(PlatformGenerator.getAllowedActionsTable()
                        .get("homepage neautentificat"));

                return;
            }
            if (page.getUser() != null) {
                PlatformGenerator.getChangedPages().add(new ChangedPage(page.getName(),
                        PlatformGenerator.getMovies().get(0)));
            }
            page.setName(pageName);
            page.setAllowedPages(PlatformGenerator.getAllowedPagesTable().get(pageName));
            page.setAllowedActions(PlatformGenerator.getAllowedActionsTable().get(pageName));

            if (pageName.equals("movies")) {
                ArrayList<Movie> outputMovies = new ArrayList<>();
                for (Movie movie: page.getUser().getAllowedMovies()) {
                    outputMovies.add(new Movie(movie));
                }
                PlatformGenerator.getOutput().addPOJO(new Output(outputMovies,
                        new User(page.getUser())));
                page.setSeenMovies(page.getUser().getAllowedMovies());
            } else {
                page.setSeenMovies(new ArrayList<Movie>());
            }

            return;
        }
        PlatformGenerator.getOutput().addPOJO(new Output("Error"));
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }
}
