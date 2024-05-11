package api.Media;

import java.io.Serializable;

public class Episode implements Serializable {
    private int duration;

    public Episode(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
