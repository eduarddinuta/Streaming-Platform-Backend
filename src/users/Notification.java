package users;

public class Notification {
    String movieName;
    String message;

    public Notification(String movieName, String message) {
        this.movieName = movieName;
        this.message = message;
    }

    public Notification(Notification notification) {
        this.movieName = notification.getMovieName();
        this.message = notification.getMessage();
    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
