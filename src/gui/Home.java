package gui;

import api.Data;
import api.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Home {
    private Data data;
    private JFrame frame;
    public Home(Data data, User user) {
        if (user.isAdmin()) {
            frame = new JFrame("FlixTV - Admin");
        } else {
            frame = new JFrame("FlixTV");
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600,600));
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        this.data = data;
        buildUI(user);
        frame.setVisible(true);
    }

    private void buildUI(User user) {
        JMenuBar menuBar = new JMenuBar();

        if (user.isAdmin()) {
            JMenu menuMovie = new JMenu("Movie");
            JMenuItem moviesAdd = new JMenuItem("Add");
            moviesAdd.addActionListener(e -> new MovieDialog(frame, data, "Add Movie", null,user));
            menuMovie.add(moviesAdd);
            JMenuItem moviesList = new JMenuItem("List");
            moviesList.addActionListener(e -> new MovieListDialog(frame, data, user));
            menuMovie.add(moviesList);

            JMenu menuSeries = new JMenu("Series");
            JMenuItem seriesAdd = new JMenuItem("Add");
            seriesAdd.addActionListener(e -> new SeriesDialog(frame, data, "Add Series", null,user));
            menuSeries.add(seriesAdd);
            JMenuItem seriesList = new JMenuItem("List");
            seriesList.addActionListener(e -> new SeriesListDialog(frame, data, user));
            menuSeries.add(seriesList);

            JMenu menuSearch = new JMenu("Search");
            JMenuItem initSearch = new JMenuItem("Search for content");
            initSearch.addActionListener(e -> new SearchDialog(frame,data,user));
            menuSearch.add(initSearch);

            JMenu menuLogout = new JMenu("Logout");
            JMenuItem log = new JMenuItem("Logout");
            log.addActionListener(e -> {
                new Welcome(data);
                frame.dispose();
            });
            menuLogout.add(log);

            menuBar.add(menuMovie);
            menuBar.add(menuSeries);
            menuBar.add(menuSearch);
            menuBar.add(menuLogout);
        } else {
            JMenu menuMovie = new JMenu("Movie");
            JMenuItem moviesList = new JMenuItem("List");
            moviesList.addActionListener(e -> new MovieListDialog(frame, data,user));
            menuMovie.add(moviesList);

            JMenu menuSeries = new JMenu("Series");
            JMenuItem seriesList = new JMenuItem("List");
            seriesList.addActionListener(e -> new SeriesListDialog(frame, data, user));
            menuSeries.add(seriesList);

            JMenu menuFavorites = new JMenu("Favorites");
            JMenuItem favoritesList = new JMenuItem("List");
            favoritesList.addActionListener(e -> new FavoritesDialog(frame,user,data));
            menuFavorites.add(favoritesList);

            JMenu menuSearch = new JMenu("Search");
            JMenuItem initSearch = new JMenuItem("Search for content");
            initSearch.addActionListener(e -> new SearchDialog(frame,data,user));
            menuSearch.add(initSearch);

            JMenu menuLogout = new JMenu("Logout");
            JMenuItem log = new JMenuItem("Logout");
            log.addActionListener(e -> {
                new Welcome(data);
                frame.dispose();
            });
            menuLogout.add(log);

            menuBar.add(menuMovie);
            menuBar.add(menuSeries);
            menuBar.add(menuFavorites);
            menuBar.add(menuSearch);
            menuBar.add(menuLogout);
        }
        frame.setJMenuBar(menuBar);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                data.save();
            }
        });
    }
}
