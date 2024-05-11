package gui;

import api.Data;
import api.Media.Category;
import api.Search;
import api.User;

import javax.swing.*;
import java.awt.*;

public class SearchDialog extends JDialog {
    public SearchDialog(JFrame frame, Data data, User user) {
        super(frame,"Search for Content",true);
        setLocation(frame.getX() + 200,frame.getY() + 200);
        buildUI(data,user);
        pack();
        setVisible(true);
    }

    private void buildUI(Data data,User user) {
        JPanel panelData = new JPanel();
        add(panelData, BorderLayout.CENTER);
        panelData.setLayout(new BorderLayout());

        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new GridLayout(0,1));
        panelData.add(panelLabels, BorderLayout.LINE_START);

        JPanel panelTextFields = new JPanel();
        panelTextFields.setLayout(new GridLayout(0,1));
        panelData.add(panelTextFields, BorderLayout.CENTER);

        String[] fields = {"Title:", "Type:", "Age restriction:", "Star:","Category:","Rating score"};
        for (String field : fields) {
            JLabel label = new JLabel(field);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            panelLabels.add(label);
        }
        JTextField title = new JTextField(20);
        panelTextFields.add(title);

        String[] optionsType = {null,"Movie","Series"};
        JComboBox<String> type = new JComboBox<>(optionsType);
        type.setSelectedItem(null);
        panelTextFields.add(type);

        String[] options = {null,"Yes","No"};
        JComboBox<String> ageRestriction = new JComboBox<>(options);
        ageRestriction.setSelectedItem(null);
        panelTextFields.add(ageRestriction);

        JTextField star = new JTextField(40);
        panelTextFields.add(star);

        Category[] categories = {null,Category.ACTION,Category.DRAMA,Category.COMEDY,Category.SCI_FI,Category.THRILLER};
        JComboBox<Category> category = new JComboBox<>(categories);
        category.setSelectedItem(null);
        panelTextFields.add(category);

        Double[] optionsRating = {null,1.0,2.0,3.0,4.0,5.0};
        JComboBox<Double> rating = new JComboBox<>(optionsRating);
        rating.setSelectedItem(null);
        panelTextFields.add(rating);

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel,BorderLayout.PAGE_END);

        JButton search = new JButton("Search");
        search.addActionListener(e -> {
            Search searchContent = new Search(data,title.getText(), (String) type.getSelectedItem(), (String) ageRestriction.getSelectedItem(),star.getText(), (Category) category.getSelectedItem(), (Double) rating.getSelectedItem());
            new ResultsListDialog((JFrame)getParent(),searchContent,user,data);
        });
        buttonsPanel.add(search);
    }
}
