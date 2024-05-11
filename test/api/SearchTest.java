package api;

import api.Media.Category;
import api.Media.Content;
import api.Media.Movie;
import api.Media.Series;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void results() {
        Data data = new Data();
        Movie movie = new Movie("ab","adsa","Yes","abc,cba,cas", Category.ACTION,"2021",90);
        Series series = new Series("aa","agdfs","Yes","acs",Category.DRAMA);
        data.addMovie(movie);
        data.addSeries(series);
        ArrayList<Content> content = new ArrayList<>();
        content.add(movie);
        content.add(series);
        Search search = new Search(data,"",null,"Yes","",null,null);
        assertEquals(content,search.results());
    }
}