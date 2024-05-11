package gui;

import api.Data;
import api.Media.Content;
import api.Media.Movie;
import api.Media.Series;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;


public class FavoritesDialog extends JDialog {
    private JTable table;
    private JButton remove;
    public FavoritesDialog(JFrame parent, User user, Data data) {
        super(parent,"Favorites",true);
        setLocation(parent.getX() + 200,parent.getY() + 200);
        buildUI(user,data);
        pack();
        setVisible(true);
    }

    private void buildUI(User user,Data data)  {
        table = new JTable(new AbstractTableModel() {

            final String[] columnNames = {"Title","Type","Category","Age Restriction","Stars"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return user.getFavorites().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Content media = user.getFavorites().get(row);
                return switch (col) {
                    case 0 -> media.getTitle();
                    case 1 -> media.getType();
                    case 2 -> media.getCategory();
                    case 3 -> media.getAgeRestriction();
                    case 4 -> media.getStars();
                    default -> null;
                };
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        add(buttonPanel,BorderLayout.PAGE_END);

        JButton preview = new JButton("Preview");
        preview.setEnabled(false);
        preview.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Content media = user.getFavorites().get(modelRow);
                if (media.getType().equals("Movie")) {
                    new Preview((JFrame) getParent(), data, "Preview Movie", (Movie) media, user);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                } else {
                    new Preview((JFrame) getParent(), data, "Preview Movie", (Series) media, user);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            }
        });
        buttonPanel.add(preview);

        remove = new JButton("Remove from Favorites");
        remove.setEnabled(false);
        remove.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                user.getFavorites().remove(modelRow);
                JOptionPane.showMessageDialog(this.getParent(),"Successfully removed","Remove from Favorites",JOptionPane.PLAIN_MESSAGE);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonPanel.add(remove);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            remove.setEnabled(rowSelected);
            preview.setEnabled(rowSelected);
        });
    }
}
