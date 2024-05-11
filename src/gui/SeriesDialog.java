package gui;

import api.Data;
import api.Media.Category;
import api.Media.Series;
import api.User;

import javax.swing.*;
import java.awt.*;

public class SeriesDialog extends JDialog {
    private Data data;
    private Series series;

    public SeriesDialog(JFrame parent, Data data, String title, Series series, User user) {
        super(parent, title, true);
        if (series == null) {
            setLocation(parent.getX() + 100, parent.getY() + 100);
        } else {
            setLocation(parent.getX() + 200, parent.getY() + 200);
        }
        this.data = data;
        this.series = series;
        buildUI(user);
        pack();
        setVisible(true);
    }

    private void buildUI(User user) {
        JPanel panelData = new JPanel();
        add(panelData, BorderLayout.CENTER);
        panelData.setLayout(new BorderLayout());

        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new GridLayout(0,1));
        panelData.add(panelLabels, BorderLayout.LINE_START);

        JPanel panelTextFields = new JPanel();
        panelTextFields.setLayout(new GridLayout(0,1));
        panelData.add(panelTextFields, BorderLayout.CENTER);
        String[] fields = {"Title:", "Description:", "Age Restriction:", "Category:", "Stars:","Similar series:"};
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

        Category[] categories = {Category.ACTION,Category.DRAMA,Category.COMEDY,Category.SCI_FI,Category.THRILLER};
        JComboBox<Category> category = new JComboBox<>(categories);
        category.setSelectedItem(null);
        panelTextFields.add(category);

        JTextField stars = new JTextField(30);
        panelTextFields.add(stars);

        JButton similar = new JButton("View Similar movies");
        similar.addActionListener(e -> new SimilarListDialog((JFrame)getParent(),"Similar Series",user,data,series));
        if (series == null) similar.setEnabled(false);
        panelTextFields.add(similar);

        if (series != null) {
            title.setText(series.getTitle());
            description.setText(series.getDescription());
            ageRestriction.setSelectedItem(series.getAgeRestriction());
            category.setSelectedItem(series.getCategory());
            stars.setText(series.getStars());
        }

        JPanel panelButtons = new JPanel();

        JButton buttonOk = new JButton("Ok");
        buttonOk.addActionListener(e -> {
            if (title.getText().isEmpty() || description.getText().isEmpty() || ageRestriction.getSelectedItem() == null || category.getSelectedItem() == null || stars.getText().isEmpty()) {
                JOptionPane.showMessageDialog(SeriesDialog.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                if (series == null) {
                    series = new Series(title.getText(), description.getText(), (String) ageRestriction.getSelectedItem(), stars.getText(), (Category) category.getSelectedItem());
                    data.addSeries(series);
                } else {
                    series.setTitle(title.getText());
                    series.setDescription(description.getText());
                    series.setAgeRestriction((String) ageRestriction.getSelectedItem());
                    series.setStars(stars.getText());
                    series.setCategory((Category) category.getSelectedItem());
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
