package input;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ContainsInput {
    private ArrayList<String> genre;
    private ArrayList<String> actors;

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genre = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }
}
