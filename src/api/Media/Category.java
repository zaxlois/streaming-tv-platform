package api.Media;

import java.io.Serializable;

public enum Category implements Serializable {
    ACTION,THRILLER,SCI_FI,COMEDY,DRAMA;


    @Override
    public String toString() {
        return switch (super.toString()) {
            case "ACTION" -> "Action";
            case "THRILLER" -> "Thriller";
            case "SCI_FI" -> "Science Fiction";
            case "COMEDY" -> "Comedy";
            case "DRAMA" -> "Drama";
            default -> null;
        };
    }
}
