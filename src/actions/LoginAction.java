package actions;

import input.CredentialsInput;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

public final class LoginAction extends ActionVisitor {

    private CredentialsInput userCredentials;
    public LoginAction(final CredentialsInput userCredentials) {
        this.userCredentials = userCredentials;
        actionName = "login";
    }

    /**
     * Logins a user to the platform if the given credentials are found in the database.
     * If not outputs an error and returns to the unauthentified page.
     * @param page
     */
    public void visit(final ConcretePage page) {
        String name = userCredentials.getName();
        String password = userCredentials.getPassword();

        for (User user: PlatformGenerator.getUsers()) {
            String currentName = user.getCredentials().getName();
            String currentPass = user.getCredentials().getPassword();

            if (name.equals(currentName) && password.equals(currentPass)) {

                page.setName("homepage autentificat");
                page.setUser(user);
                page.setAllowedPages(PlatformGenerator.getAllowedPagesTable()
                        .get("homepage autentificat"));
                page.setAllowedActions(PlatformGenerator.getAllowedActionsTable()
                        .get("homepage autentificat"));
                PlatformGenerator.getOutput().addPOJO(new Output(new User(user)));
                return;
            }
        }
        PlatformGenerator.getOutput().addPOJO(new Output("Error"));

        page.setName("homepage neautentificat");
        page.setAllowedPages(PlatformGenerator.getAllowedPagesTable()
                .get("homepage neautentificat"));
        page.setAllowedActions(PlatformGenerator.getAllowedActionsTable()
                .get("homepage neautentificat"));

    }

    public CredentialsInput getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(final CredentialsInput userCredentials) {
        this.userCredentials = userCredentials;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }
}
