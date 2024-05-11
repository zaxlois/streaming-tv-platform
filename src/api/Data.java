package api;

import api.Media.Content;
import api.Media.Movie;
import api.Media.Series;

import java.io.*;
import java.util.ArrayList;

public class Data implements Serializable {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;

    public Data() {
        users = new ArrayList<>();
        movies = new ArrayList<>();
        series = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Series> getSeries() {
        return series;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addSeries(Series TVSeries) {
        series.add(TVSeries);
    }

    public boolean checkLoginInfo(String username,String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public boolean addUser(String firstName,String lastName,String username,String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return false;
        }
        users.add(new User(firstName,lastName,username,password));
        return true;
    }

    public void deleteFromDatabase(Content media) {
        for (User user : users) {
            if (!user.isAdmin()) {
                user.getFavorites().remove(media);
            }
        }
        if (media.getType().equals("Movie")) {
            for (Movie movie : movies) {
                movie.getSimilar().remove(media);
            }
        } else {
            for (Series tv : series) {
                tv.getSimilar().remove(media);
            }
        }
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"));) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.dat"));) {
            Data data = (Data) ois.readObject();
            users = data.users;
            movies = data.movies;
            series = data.series;
        } catch (FileNotFoundException e) {
            // do nothing
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
