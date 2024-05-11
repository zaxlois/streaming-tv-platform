package gui;

import api.Data;
import api.Media.Movie;
import api.Media.Series;
import api.User;

import javax.swing.*;
import java.awt.*;

public class Preview extends JDialog {
    private User user;
    private Data data;
    public Preview(JFrame parent, Data data, String title, Series series, User user) {
        super(parent,title,true);
        setLocation(parent.getX() + 200,parent.getY() + 200);
        this.data = data;
        this.user = user;
        buildUI(series);
        pack();
        setVisible(true);
    }

    public Preview(JFrame parent, Data data, String title, Movie movie, User user) {
        super(parent,title,true);
        setLocation(parent.getX() + 200,parent.getY() + 200);
        this.data = data;
        this.user = user;
        buildUI(movie);
        pack();
        setVisible(true);
    }

    private void buildUI(Movie movie) {
        JPanel dataPanel = new JPanel(new BorderLayout());
        add(dataPanel,BorderLayout.CENTER);

        JPanel labelPanel = new JPanel(new GridLayout(0,1));
        dataPanel.add(labelPanel,BorderLayout.LINE_START);

        String[] labelNames = {"Title:","Description:","Age Restriction:","Premiere Year:","Duration:","Category:", "Stars:" ,"Ratings:","Average Rating:","Similar Movies:"};
        for (String name : labelNames) {
            JLabel label = new JLabel(name);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labelPanel.add(label);
        }

        JPanel contentPanel = new JPanel(new GridLayout(0,1));
        dataPanel.add(contentPanel,BorderLayout.CENTER);

        JLabel title = new JLabel(movie.getTitle());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(title);

        JLabel description = new JLabel(movie.getDescription());
        description.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(description);

        JLabel ageRestriction = new JLabel(movie.getAgeRestriction());
        ageRestriction.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(ageRestriction);

        JLabel premiereYear = new JLabel(movie.getPremiereYear());
        premiereYear.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(premiereYear);

        JLabel duration = new JLabel(movie.getDuration() + " minutes");
        duration.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(duration);

        JLabel category = new JLabel(movie.getCategory().toString());
        category.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(category);

        JLabel stars = new JLabel(movie.getStars());
        stars.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(stars);

        JLabel totalReviews = new JLabel(movie.getNumberOfReviews() + "");
        totalReviews.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(totalReviews);

        JLabel avgRating = new JLabel(movie.AverageRating() + "");
        avgRating.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(avgRating);

        JButton similar = new JButton("View Similar movies");
        similar.addActionListener(e -> new SimilarListDialog((JFrame)getParent(),"Similar Movies",user,data,movie));
        contentPanel.add(similar);

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.PAGE_END);

        JButton reviewList = new JButton("View Reviews");
        reviewList.addActionListener(e -> new ReviewListDialog((JFrame)getParent(),"Reviews",user,movie));
        buttonsPanel.add(reviewList);

        if (user.isAdmin()) {
            JButton edit = new JButton("Edit");
            edit.addActionListener(e -> new MovieDialog((JFrame) getParent(),data,"Edit Movie",movie,user));
            buttonsPanel.add(edit);

            JButton delete = new JButton("Delete");
            delete.addActionListener(e -> {
                data.deleteFromDatabase(movie);
                data.getMovies().remove(movie);
                dispose();
            });
            buttonsPanel.add(delete);
        } else {
            JButton addToFavorites = new JButton("Add to Favorites");
            JButton removeFromFavorites = new JButton("Remove from Favorites");

            if (user.getFavorites().contains(movie)) {
                addToFavorites.setVisible(false);
            } else {
                removeFromFavorites.setVisible(false);
            }

            addToFavorites.addActionListener(e -> {
                user.getFavorites().add(movie);
                JOptionPane.showMessageDialog(this.getParent(),"Successfully added","Add to Favorites",JOptionPane.PLAIN_MESSAGE);
                addToFavorites.setVisible(false);
                removeFromFavorites.setVisible(true);
            });
            buttonsPanel.add(addToFavorites);

            removeFromFavorites.addActionListener(e ->{
                user.getFavorites().remove(movie);
                JOptionPane.showMessageDialog(this.getParent(),"Successfully removed","Remove from Favorites",JOptionPane.PLAIN_MESSAGE);
                removeFromFavorites.setVisible(false);
                addToFavorites.setVisible(true);
            });
            buttonsPanel.add(removeFromFavorites);

        }
    }

    private void buildUI(Series series) {
        JPanel dataPanel = new JPanel(new BorderLayout());
        add(dataPanel,BorderLayout.CENTER);

        JPanel labelPanel = new JPanel(new GridLayout(0,1));
        dataPanel.add(labelPanel,BorderLayout.LINE_START);

        String[] labelNames = {"Title:","Description:","Age Restriction:","Category:","Stars:","Ratings:","Average Rating:","Similar Series"};
        for (String name : labelNames) {
            JLabel label = new JLabel(name);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labelPanel.add(label);
        }

        JPanel contentPanel = new JPanel(new GridLayout(0,1));
        dataPanel.add(contentPanel,BorderLayout.CENTER);

        JLabel title = new JLabel(series.getTitle());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(title);

        JLabel description = new JLabel(series.getDescription());
        description.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(description);

        JLabel ageRestriction = new JLabel(series.getAgeRestriction());
        ageRestriction.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(ageRestriction);

        JLabel category = new JLabel(series.getCategory().toString());
        category.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(category);

        JLabel stars = new JLabel(series.getStars());
        stars.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(stars);

        JLabel totalReviews = new JLabel(series.getNumberOfReviews() + "");
        totalReviews.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(totalReviews);

        JLabel avgRating = new JLabel(series.AverageRating() + "");
        avgRating.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(avgRating);

        JButton similar = new JButton("View Similar Series");
        similar.addActionListener(e -> new SimilarListDialog((JFrame)getParent(),"Similar Series",user,data,series));
        contentPanel.add(similar);

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.PAGE_END);

        JButton reviewList = new JButton("View Reviews");
        reviewList.addActionListener(e -> new ReviewListDialog((JFrame)getParent(),"Reviews",user,series));
        buttonsPanel.add(reviewList);

        JButton seasonList = new JButton("View Seasons");
        seasonList.addActionListener(e -> new SeasonListDialog((JFrame)getParent(),"Seasons",user,series));
        buttonsPanel.add(seasonList);

        if (user.isAdmin()) {
            JButton edit = new JButton("Edit");
            edit.addActionListener(e -> new SeriesDialog((JFrame) getParent(),data,"Edit Series",series,user));
            buttonsPanel.add(edit);

            JButton delete = new JButton("Delete");
            delete.addActionListener(e -> {
                data.deleteFromDatabase(series);
                data.getSeries().remove(series);
                dispose();
            });
            buttonsPanel.add(delete);
        } else {
            JButton addToFavorites = new JButton("Add to Favorites");
            JButton removeFromFavorites = new JButton("Remove from Favorites");

            addToFavorites.addActionListener(e -> {
                user.getFavorites().add(series);
                JOptionPane.showMessageDialog(this.getParent(),"Successfully added","Add to Favorites",JOptionPane.PLAIN_MESSAGE);
                addToFavorites.setVisible(false);
                removeFromFavorites.setVisible(true);
            });
            buttonsPanel.add(addToFavorites);

            removeFromFavorites.addActionListener(e ->{
                user.getFavorites().remove(series);
                JOptionPane.showMessageDialog(this.getParent(),"Successfully removed","Remove from Favorites",JOptionPane.PLAIN_MESSAGE);
                removeFromFavorites.setVisible(false);
                addToFavorites.setVisible(true);
            });
            buttonsPanel.add(removeFromFavorites);

            if (user.getFavorites().contains(series)) {
                addToFavorites.setVisible(false);
            } else {
                removeFromFavorites.setVisible(false);
            }
        }
    }
}
