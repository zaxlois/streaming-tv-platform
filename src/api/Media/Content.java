package api.Media;

import api.User;

import java.io.Serializable;
import java.util.ArrayList;


public class Content implements Serializable {
    private String title;
    private String description;
    private String ageRestriction;
    private String stars;
    private Category category;
    private ArrayList<Review> reviews;
    private ArrayList<Content> similar;
    public Content(String title, String description, String ageRestriction, String stars, Category category) {
        this.title = title;
        this.description = description;
        this.ageRestriction = ageRestriction;
        this.stars = stars;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.similar = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addReview(User user, String text, int rating) {
        if (rating >=1 && rating <= 5) {
            reviews.add(new Review(user, text, rating));
            System.out.println("IN addReview : Successfully added.");
        } else {
            System.out.println("IN addReview : Error on rating number.");
        }
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public int getNumberOfReviews() {
        return reviews.size();
    }
    private int totalRating() {
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum;
    }
    public double AverageRating() {
        return (double) totalRating() / getNumberOfReviews();
    }

    public boolean addMediaToSimilar(Content content) {
        if (!similar.contains(content)) {
            similar.add(content);
            return true;
        }
        return false;
    }

    public ArrayList<Content> getSimilar() {
        return similar;
    }

    public String getType() {
        if (this instanceof Movie) return "Movie";
        return "Series";
    }
}
