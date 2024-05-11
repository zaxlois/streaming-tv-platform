package api.Media;



import api.User;

import java.io.Serializable;

public class Review implements Serializable {
    private User user;
    private String text;
    private int rating;

    public Review(User user, String text, int rating) {
        this.user = user;
        this.text = text;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
    public String getUsername() {
        return user.getUsername();
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
