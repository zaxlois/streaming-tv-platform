package api.Media;


import java.io.Serializable;
import java.util.ArrayList;

public class Series extends Content implements Serializable {
    private ArrayList<Season> seasons;

    public Series(String title, String description, String ageRestriction, String stars, Category category) {
        super(title, description, ageRestriction, stars, category);
        seasons = new ArrayList<>();
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeason(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
    public void setSeasons(int seasons) {

    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public void removeSeason(Season season) {
        seasons.remove(season);
    }
}
