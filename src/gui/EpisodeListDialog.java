package gui;

import api.Media.Episode;
import api.Media.Season;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EpisodeListDialog extends JDialog {
    private JTable table;
    private JButton edit;
    private JButton delete;
    public EpisodeListDialog(JFrame parent, String title, User user, Season season) {
        super(parent,title,true);
        setLocation(parent.getX() + 200, parent.getY() + 200);
        buildUI(user,season);
        pack();
        setVisible(true);
    }

    private void buildUI(User user,Season season) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Episode Number", "Duration"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return season.getEpisodes().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Episode episode = season.getEpisodes().get(row);
                return switch (col) {
                    case 0 -> row + 1;
                    case 1 -> episode.getDuration();
                    default -> null;
                };
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            edit.setEnabled(rowSelected);
            delete.setEnabled(rowSelected);
        });

        JPanel panel = new JPanel();
        add(panel,BorderLayout.PAGE_END);
        if (user.isAdmin()) {
            JButton add = new JButton("Add");
            panel.add(add);
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new EpisodeDialog((JFrame) getParent(), "Add Episode", season,null);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            edit = new JButton("Edit");
            edit.setEnabled(false);
            edit.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    Episode episode = season.getEpisodes().get(modelRow);
                    new EpisodeDialog((JFrame) getParent(), "Edit Episode", season,episode);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            panel.add(edit);

            delete = new JButton("Delete");
            delete.setEnabled(false);
            delete.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    season.getEpisodes().remove(modelRow);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            panel.add(delete);
        }
    }
}
