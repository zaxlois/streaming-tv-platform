package gui;

import api.Data;
import api.Media.Category;
import api.Media.Movie;
import api.User;

import javax.swing.*;
import java.awt.*;

public class MovieDialog extends JDialog {
    private Data data;
    private Movie movie;

    public MovieDialog(JFrame parent, Data data, String title, Movie movie, User user) {
        super(parent, title, true);
        if (movie == null) {
            setLocation(parent.getX() + 100, parent.getY() + 100);
        } else {
            setLocation(parent.getX() + 200, parent.getY() + 200);
        }
        this.data = data;
        this.movie = movie;
        buildUI(user);
        pack();
        setVisible(true);
    }

    final private void buildUI(User user) {
        JPanel panelData = new JPanel();
        add(panelData, BorderLayout.CENTER);
        panelData.setLayout(new BorderLayout());
        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new GridLayout(0,1));
        panelData.add(panelLabels, BorderLayout.LINE_START);
        JPanel panelTextFields = new JPanel();
        panelTextFields.setLayout(new GridLayout(0,1));
        panelData.add(panelTextFields, BorderLayout.CENTER);
        String[] fields = {"Title:", "Description:", "Age restriction:", "Premiere year:", "Duration:","Category:","Stars:","Similar movies:"};
        for (String field : fields) {
            JLabel label = new JLabel(field);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            panelLabels.add(label);
        }
        JTextField title = new JTextField(20);
        panelTextFields.add(title);

        JTextField description = new JTextField(40);
        panelTextFields.add(description);

        String[] options = {"Yes","No"};
        JComboBox<String> ageRestriction = new JComboBox<>(options);
        ageRestriction.setSelectedItem(null);
        panelTextFields.add(ageRestriction);

        JTextField premiereYear = new JTextField(20);
        panelTextFields.add(premiereYear);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(90,0,1000,1);
        JSpinner spinnerDuration = new JSpinner(spinnerModel);
        panelTextFields.add(spinnerDuration);

        Category[] categories = {Category.ACTION,Category.DRAMA,Category.COMEDY,Category.SCI_FI,Category.THRILLER};
        JComboBox<Category> category = new JComboBox<>(categories);
        category.setSelectedItem(null);
        panelTextFields.add(category);

        JTextField stars = new JTextField(40);
        panelTextFields.add(stars);

        JButton similar = new JButton("View Similar movies");
        similar.addActionListener(e -> new SimilarListDialog((JFrame)getParent(),"Similar Movies",user,data,movie));
        if (movie == null) {
            similar.setEnabled(false);
        }
        panelTextFields.add(similar);

        if (movie != null) {
            title.setText(movie.getTitle());
            description.setText(movie.getDescription());
            ageRestriction.setSelectedItem(movie.getAgeRestriction());
            premiereYear.setText(movie.getPremiereYear());
            spinnerModel.setValue(Integer.valueOf(movie.getDuration()));
            category.setSelectedItem(movie.getCategory());
            stars.setText(movie.getStars());
        }

        JPanel panelButtons = new JPanel();
        JButton buttonOk = new JButton("Ok");
        buttonOk.addActionListener(e -> {
            if (title.getText().isEmpty() || description.getText().isEmpty() || ageRestriction.getSelectedItem() == null || premiereYear.getText().isEmpty() || category.getSelectedItem() == null || stars.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MovieDialog.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                if (movie == null) {
                    movie = new Movie(title.getText(), description.getText(), (String) ageRestriction.getSelectedItem(), stars.getText(), (Category) category.getSelectedItem(), premiereYear.getText(), spinnerModel.getNumber().intValue());
                    data.addMovie(movie);
                } else {
                    movie.setTitle(title.getText());
                    movie.setDescription(description.getText());
                    movie.setAgeRestriction((String) ageRestriction.getSelectedItem());
                    movie.setPremiereYear(premiereYear.getText());
                    movie.setDuration(spinnerModel.getNumber().intValue());
                    movie.setCategory((Category) category.getSelectedItem());
                    movie.setStars(stars.getText());
                }
                dispose();
            }
        });
        panelButtons.add(buttonOk);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> dispose());
        panelButtons.add(buttonCancel);
        add(panelButtons, BorderLayout.PAGE_END);
    }
}
