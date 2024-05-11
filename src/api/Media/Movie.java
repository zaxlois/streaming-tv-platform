package api.Media;

import java.io.Serializable;

public class Movie extends api.Media.Content implements Serializable {
    private String premiereYear;
    private int duration;

    public Movie(String title, String description, String ageRestriction, String stars,Category category, String premiereYear, int duration) {
        super(title, description, ageRestriction, stars, category);
        this.premiereYear = premiereYear;
        this.duration = duration;
    }

    public String getPremiereYear() {
        return premiereYear;
    }

    public void setPremiereYear(String premiereYear) {
        this.premiereYear = premiereYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
