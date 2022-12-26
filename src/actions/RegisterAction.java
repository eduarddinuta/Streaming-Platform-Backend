package actions;

import input.CredentialsInput;
import output.Output;
import pages.ConcretePage;
import platform.PlatformGenerator;
import users.User;

public final class RegisterAction extends ActionVisitor {

    private CredentialsInput userCredentials;
    public RegisterAction(final CredentialsInput userCredentials) {
        this.userCredentials = userCredentials;
        actionName = "register";
    }

    /**
     * Adds a new user to the database and automatically logins. If the user already
     * exists outputs an error.
     * @param page
     */
    @Override
    public void visit(final ConcretePage page) {

        for (User user: PlatformGenerator.getUsers()) {
            if (user.getCredentials().getName().equals(userCredentials.getName())) {
                PlatformGenerator.getOutput().addPOJO(new Output("Error"));
                page.setName("homepage neautentificat");
                page.setAllowedPages(PlatformGenerator.getAllowedPagesTable()
                        .get("homepage neautentificat"));
                page.setAllowedActions(PlatformGenerator.getAllowedActionsTable()
                        .get("homepage neautentificat"));
                return;
            }
        }

        User newUser = new User(new CredentialsInput(userCredentials));
        PlatformGenerator.getUsers().add(newUser);


        page.setName("homepage autentificat");
        page.setUser(newUser);
        page.setAllowedPages(PlatformGenerator.getAllowedPagesTable()
                .get("homepage autentificat"));
        page.setAllowedActions(PlatformGenerator.getAllowedActionsTable()
                .get("homepage autentificat"));
        PlatformGenerator.getOutput().addPOJO(new Output(new User(userCredentials)));
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
