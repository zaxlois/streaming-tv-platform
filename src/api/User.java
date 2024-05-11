package api;

import api.Media.Content;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Κλάση που υλοποιεί την καταχώρηση ενός χρήστη
 */
public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean admin;
    private ArrayList<Content> favorites;

    /**
     * Κατασκευαστής που υλοποιεί το προφίλ ενός χρήστη
     * @param firstName το όνομα του χρήστη
     * @param lastName το επώνυμο του χρήστη
     * @param username το username του χρήστη
     * @param password ο κωδικός του χρήστη
     */
    public User(String firstName,String lastName,String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.favorites = new ArrayList<>();
        admin = (username.equals("admin1") && password.equals("password1")) || (username.equals("admin2") && password.equals("password2"));
    }

    /**
     * Getter για το username
     * @return To username
     */
    public String getUsername() {return username;}

    /**
     * Getter για το password
     * @return
     */
    public String getPassword() {return password;}
    public boolean isAdmin() {return admin;}
    public ArrayList<Content> getFavorites() {
        return favorites;
    }
}
