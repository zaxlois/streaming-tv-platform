package gui;

import api.Media.Content;
import api.Media.Review;
import api.User;

import javax.swing.*;
import java.awt.*;

public class ReviewDialog extends JDialog {
    public ReviewDialog(JFrame parent, String title, User user, Content media, Review review) {
        super(parent,title,true);
        setLocation(parent.getX() + 200, parent.getY() + 200);
        buildUI(user,media,review);
        pack();
        setVisible(true);
    }

    private void buildUI(User user, Content media,Review review) {
        JPanel panelData = new JPanel(new BorderLayout());
        add(panelData,BorderLayout.CENTER);

        JPanel labelPanel = new JPanel(new GridLayout(0,1));
        panelData.add(labelPanel,BorderLayout.LINE_START);

        JLabel textLabel = new JLabel("Review text:");
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPanel.add(textLabel);

        JLabel ratingLabel = new JLabel("Rating");
        ratingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPanel.add(ratingLabel);

        JPanel contentPanel = new JPanel(new GridLayout(0,1));
        panelData.add(contentPanel,BorderLayout.CENTER);

        JTextField text = new JTextField(30);
        contentPanel.add(text);

        Integer[] options = {1,2,3,4,5};
        JComboBox<Integer> rating = new JComboBox<>(options);
        rating.setSelectedItem(null);
        contentPanel.add(rating);

        if (review != null) {
            text.setText(review.getText());
            rating.setSelectedItem(review.getRating());
        }

        JPanel panelButtons = new JPanel();
        JButton buttonOk = new JButton("Ok");
        buttonOk.addActionListener(e -> {
            if (text.getText().isEmpty() || rating.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(ReviewDialog.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                if (review == null) {
                    media.addReview(user, text.getText(), rating.getSelectedIndex());
                } else {
                    review.setText(text.getText());
                    review.setRating((Integer) rating.getSelectedItem());
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
