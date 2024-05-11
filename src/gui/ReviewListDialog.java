package gui;

import api.Media.Content;
import api.Media.Review;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class ReviewListDialog extends JDialog{
    public ReviewListDialog(JFrame parent, String title, User user, Content media) {
        super(parent,title,true);
        setLocation(parent.getX() + 200,parent.getY() + 200);
        buildUI(user,media);
        pack();
        setVisible(true);
    }

    private void buildUI(User user,Content media) {
        JTable table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Username", "Review", "Rating"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return media.getReviews().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Review review = media.getReviews().get(row);
                switch (col) {
                    case 0: return review.getUsername();
                    case 1: return review.getText();
                    case 2: return review.getRating();
                    default: return null;
                }
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        if (!user.isAdmin()) {
        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.PAGE_END);

        JButton addReview = new JButton("Add Review");
        addReview.addActionListener(e -> {
            new ReviewDialog((JFrame)getParent(),"Add Review",user,media,null);
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        });
        buttonsPanel.add(addReview);

        JButton editReview = new JButton("Edit Review");
        editReview.setEnabled(false);
        editReview.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Review review = media.getReviews().get(modelRow);
                new ReviewDialog((JFrame) getParent(),"Edit Review",user,media,review);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonsPanel.add(editReview);

        JButton delete = new JButton("Delete Review");
        delete.setEnabled(false);
        delete.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                media.getReviews().remove(modelRow);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonsPanel.add(delete);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            boolean rowSelected = table.getSelectedRow() != -1;
            if (rowSelected) {
                boolean isThis = media.getReviews().get(row).getUsername().equals(user.getUsername());
                editReview.setEnabled(isThis);
                delete.setEnabled(isThis);
            }
        });
        }
    }
}
