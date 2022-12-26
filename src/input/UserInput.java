package input;

public final class UserInput {
    private CredentialsInput credentials;

    public UserInput() {

    }
    public UserInput(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }
}
