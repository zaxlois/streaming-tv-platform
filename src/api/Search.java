package api;

import api.Media.Category;
import api.Media.Content;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Κλάση που υλοποιεί την αναζήτηση ενός μέσου και από τους δύο τύπους χρηστών.
 * Δημιουργεί ένα αντικείμενο με τα κριτήρια που επέλεξε ο χρήστης και επιστρέφει τα αντίστοιχα αποτελέσματα μέσω μιας λίστας.
 */
public class Search implements Serializable {
    private String title;
    private String type;
    private String ageRestriction;
    private String stars;
    private Category category;
    private Double rating;
    private Data data;
    private boolean titleCheck;
    private boolean typeCheck;
    private boolean ageCheck;
    private boolean starsCheck;
    private boolean categoryCheck;
    private boolean ratingCheck;
    private ArrayList<Content> content;

    /**
     * Κατασκευαστής που δημιουργεί το αντικείμενο αναζήτησης και αρχικοποιεί όλα τα μέλη του
     * @param data η βάση δεδομένων
     * @param title ο τίτλος που δήλωσε ο χρήστης (μπορεί να μη δηλώσει)
     * @param type ο τύπος του μέσου που δήλωσε ο χρήστης (μπορεί να μη δηλώσει)
     * @param ageRestriction ο περιορισμός της ηλικίας που δήλωσε ο χρήστης (μπορεί να μη δηλώσει)
     * @param stars ο πρωταγωνιστής που δήλωσε ο χρήστης (μπορεί να μη δηλώσει)
     * @param category η κατηγορία του μέσου που δήλωσε ο χρήστης (μπορεί να μη δηλώσει)
     * @param rating η ελάχιστη μέση βαθμολογία αξιολογήσεων του μέσου που δήλωσε ο χρήστης (μπορεί να μη δηλώσει)
     */
    public Search(Data data,String title,String type,String ageRestriction,String stars,Category category,Double rating) {
        this.title = title;
        this.type = type;
        this.ageRestriction = ageRestriction;
        this.stars = stars;
        this.category = category;
        this.rating = rating;

        this.data = data;

        content = new ArrayList<>();
        content.addAll(data.getMovies());
        content.addAll(data.getSeries());

        titleCheck = typeCheck = ageCheck = starsCheck = categoryCheck = ratingCheck = false;
    }

    /**
     * Μέθοδος που υλοποιεί την αναζήτηση των μέσων με βάση τα κριτήρια που έδωσε ο χρήστης.
     * @return Μια λίστα με τα αποτελέσματα. Αν δε δοθεί κάποιο κριτήριο επιστρέφονται όλα τα μέσα.
     */
    public ArrayList<Content> results() {
        ArrayList<Content> searchResults = new ArrayList<>();
        for (Content media : content) {
            titleCheck = typeCheck = ageCheck = starsCheck = categoryCheck = ratingCheck = false;
            if (title.equals("") || media.getTitle().equalsIgnoreCase(title)) titleCheck = true;
            if (type == null) {
                typeCheck = true;
            } else if (type.equals(media.getType())) {
                typeCheck =true;
            }
            if (ageRestriction == null) {
                ageCheck = true;
            } else if (ageRestriction.equals(media.getAgeRestriction())) {
                ageCheck = true;
            }
            if (stars.isEmpty()) {
                starsCheck = true;
            } else {
                for (String star : media.getStars().split(",")) {
                    if (stars.equalsIgnoreCase(star)) {
                        starsCheck = true;
                        break;
                    }
                }
            }
            if (category == null) {
                categoryCheck = true;
            } else if (category == media.getCategory()) {
                categoryCheck = true;
            }
            if (rating == null) {
                ratingCheck = true;
            } else if (Double.compare(rating,-1)==0 || Double.compare(rating, media.AverageRating())<0) {
                ratingCheck = true;
            }
            if (titleCheck && typeCheck && ageCheck && starsCheck && categoryCheck && ratingCheck) {
                searchResults.add(media);
            }
        }
        return searchResults;
    }
}
