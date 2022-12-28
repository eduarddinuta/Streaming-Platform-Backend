package platform;

import actions.ActionVisitor;
import input.ActionInput;
import input.CredentialsInput;
import input.MovieInput;
import input.UserInput;
import movies.Movie;
import output.Output;
import pages.ChangedPage;
import pages.ConcretePage;
import users.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import factories.ActionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class PlatformGenerator {
    private static PlatformGenerator instance = null;
    private static ArrayNode output;
    private static ConcretePage currentPage;
    private ArrayList<ActionInput> actions;
    private static ArrayList<User> users;
    private static ArrayList<Movie> movies;
    private static ArrayList<ChangedPage> changedPages;
    private static HashMap<String, ArrayList<String>> allowedActionsTable = new HashMap<>();
    private static HashMap<String, ArrayList<String>> allowedPagesTable = new HashMap<>();
    private PlatformGenerator() {

    }

    /**
     * gets the singleton instance for PlatformGenerator
     * @return
     */
    public static PlatformGenerator getInstance() {
        if (instance == null) {
            instance = new PlatformGenerator();
        }

        return instance;
    }
    /**
     * Starts the execution of the streaming platform. Initializes the platform's parameters
     * thenIterates through every action and calls the corresponding function using the
     * visitor pattern.
     */
    public void startPage(final ArrayList<ActionInput> actionsInput,
                          final ArrayList<UserInput> usersInput,
                          final ArrayList<MovieInput> moviesInput, final ArrayNode outputNode) {

        this.actions = actionsInput;
        this.users = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.changedPages = new ArrayList<>();
        for (MovieInput movie: moviesInput) {
            this.movies.add(new Movie(movie.getName(), movie.getYear(),
                    movie.getDuration(), movie.getGenres(),
                    movie.getActors(), movie.getCountriesBanned()));
        }

        for (UserInput user: usersInput) {
            this.users.add(new User(new CredentialsInput(user.getCredentials())));
        }

        this.output = outputNode;

        buildAllowedActionsTable();
        buildAllowedPagesTable();

        currentPage = new ConcretePage("homepage neautentificat",
                null, allowedActionsTable.get("homepage neautentificat"),
                allowedPagesTable.get("homepage neautentificat"));
        for (ActionInput action: actions) {
            ActionVisitor actionVisitor = ActionFactory.getInstance().createAction(action);
            if (currentPage.getAllowedActions().contains(actionVisitor.getActionName())) {
                    currentPage.accept(actionVisitor);
            } else {
                PlatformGenerator.getOutput().addPOJO(new Output("Error"));
            }
        }

        ActionInput recommend = new ActionInput();
        recommend.setType("recommend");
        ActionVisitor recommendVisitor = ActionFactory.getInstance().createAction(recommend);
        currentPage.accept(recommendVisitor);
    }

    /**
     * Builds Hashmap for every allowed action on every page
     */
    public void buildAllowedActionsTable() {
        allowedActionsTable.put("homepage neautentificat",
                new ArrayList<String>(Arrays.asList("change page", "add", "delete", "back",
                        "recommend")));
        allowedActionsTable.put("login", new ArrayList<String>(Arrays.asList("login", "add",
                "delete", "back", "recommend")));
        allowedActionsTable.put("register", new ArrayList<String>(Arrays.asList("register", "add",
                "delete", "back", "recommend")));
        allowedActionsTable.put("homepage autentificat",
                new ArrayList<String>(Arrays.asList("change page", "add", "delete",
                        "back", "recommend")));
        allowedActionsTable.put("movies", new ArrayList<String>(Arrays.asList("change page",
                "search", "filter", "add", "delete", "back", "recommend")));
        allowedActionsTable.put("see details", new ArrayList<String>(Arrays.asList("change page",
                "purchase", "watch", "like", "rate", "add", "delete", "back", "subscribe",
                "recommend")));
        allowedActionsTable.put("upgrades", new ArrayList<String>(Arrays.asList("change page",
                "buy tokens", "buy premium account", "add", "delete", "back", "recommend")));
        allowedActionsTable.put("logout", new ArrayList<String>());
    }

    /**
     * Builds Hashmap for every directly reachable page from every page
     */
    public void buildAllowedPagesTable() {
        allowedPagesTable.put("register", new ArrayList<String>(Arrays.asList("register")));
        allowedPagesTable.put("login", new ArrayList<String>(Arrays.asList("login")));
        allowedPagesTable.put("logout", new ArrayList<String>(Arrays.asList("logout")));
        allowedPagesTable.put("homepage neautentificat", new ArrayList<String>(
                Arrays.asList("login", "register", "homepage neautentificat")));
        allowedPagesTable.put("homepage autentificat", new ArrayList<String>(
                Arrays.asList("movies", "upgrades", "logout", "homepage autentificat")));
        allowedPagesTable.put("movies", new ArrayList<String>(
                Arrays.asList("homepage autentificat", "see details", "logout", "movies")));
        allowedPagesTable.put("see details", new ArrayList<String>(
                Arrays.asList("homepage autentificat", "movies", "upgrades", "logout",
                        "see details")));
        allowedPagesTable.put("upgrades", new ArrayList<String>(
                Arrays.asList("homepage autentificat", "movies", "logout", "upgrades")));
    }

    public static ConcretePage getCurrentPage() {
        return currentPage;
    }

    public static void setCurrentPage(final ConcretePage currentPage) {
        PlatformGenerator.currentPage = currentPage;
    }

    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }

    public static HashMap<String, ArrayList<String>> getAllowedActionsTable() {
        return allowedActionsTable;
    }

    public static void setAllowedActionsTable(final HashMap<String,
            ArrayList<String>> allowedActions) {
        PlatformGenerator.allowedActionsTable = allowedActions;
    }

    public static HashMap<String, ArrayList<String>> getAllowedPagesTable() {
        return allowedPagesTable;
    }

    public static void setAllowedPagesTable(final HashMap<String, ArrayList<String>> allowedPages) {
        PlatformGenerator.allowedPagesTable = allowedPages;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(final ArrayList<User> users) {
        PlatformGenerator.users = users;
    }

    public static ArrayList<Movie> getMovies() {
        return movies;
    }

    public static void setMovies(final ArrayList<Movie> movies) {
        PlatformGenerator.movies = movies;
    }

    public static ArrayNode getOutput() {
        return output;
    }

    public static void setOutput(final ArrayNode output) {
        PlatformGenerator.output = output;
    }

    public static ArrayList<ChangedPage> getChangedPages() {
        return changedPages;
    }

    public static void setChangedPages(final ArrayList<ChangedPage> changedPages) {
        PlatformGenerator.changedPages = changedPages;
    }
}
