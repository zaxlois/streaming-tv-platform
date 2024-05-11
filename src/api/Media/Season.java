package api.Media;

import java.io.Serializable;
import java.util.ArrayList;

public class Season implements Serializable {
    private int seasonNumber;
    private int premiereYear;
    private ArrayList<Episode> episodes;

    public Season(int seasonNumber,int premiereYear) {
        this.seasonNumber = seasonNumber;
        this.premiereYear = premiereYear;
        episodes = new ArrayList<>();
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getPremiereYear() {
        return premiereYear;
    }
    public void setPremiereYear(int premiereYear) {
        this.premiereYear = premiereYear;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    public void removeEpisode(Episode episode) {
        episodes.remove(episode);
    }
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}
