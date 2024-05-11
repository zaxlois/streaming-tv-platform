package gui;

import api.Data;
import api.Media.Content;
import api.Media.Movie;
import api.Media.Series;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class SimilarListDialog extends JDialog{
    private JTable table;
    private JButton preview;
    private JButton delete;
    public SimilarListDialog(JFrame parent, String title, User user, Data data, Content media) {
        super(parent,title,true);
        setLocation(parent.getX() +200,parent.getY() + 200);
        buildUI(user,data,media);
        pack();
        setVisible(true);
    }

    private void buildUI(User user,Data data,Content media) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Title", "Category", "Age Restriction", "Stars"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return media.getSimilar().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Content content = media.getSimilar().get(row);
                return switch (col) {
                    case 0 -> content.getTitle();
                    case 1 -> content.getCategory().toString();
                    case 2 -> content.getAgeRestriction();
                    case 3 -> content.getStars();
                    default -> null;
                };
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        preview = new JButton("Preview");
        preview.setEnabled(false);
        preview.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Content content = media.getSimilar().get(modelRow);
                if (content.getType().equals("Movie")) {
                    new Preview((JFrame)getParent(),data,"Preview Movie",(Movie) content,user);
                } else {
                    new Preview((JFrame)getParent(),data,"Preview Series",(Series) content,user);
                }
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        panel.add(preview);

        if (user.isAdmin()) {
            JButton add = new JButton("Add");
            add.addActionListener(e -> {
                if (media.getType().equals("Movie")) {
                    new MovieListDialog((JFrame) getParent(),data,media);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                } else {
                    new SeriesListDialog((JFrame) getParent(),data,media);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            panel.add(add);

            delete = new JButton("Delete");
            delete.setEnabled(false);
            delete.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    media.getSimilar().remove(modelRow);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            panel.add(delete);
        }

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            delete.setEnabled(rowSelected);
            preview.setEnabled(rowSelected);
        });
        add(panel,BorderLayout.PAGE_END);
    }
}
