package gui;

import api.Media.Season;
import api.Media.Series;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class SeasonListDialog extends JDialog {
    private JTable table;
    private JButton edit;
    private JButton delete;
    private JButton episodesButton;
    public SeasonListDialog(JFrame parent, String title, User user, Series series) {
        super(parent,title,true);
        setLocation(parent.getX() + 200,parent.getY() + 200);
        buildUI(user,series);
        pack();
        setVisible(true);
    }

    private void buildUI(User user,Series series) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Season Number", "Premiere Year", "Episodes"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return series.getSeasons().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Season season = series.getSeasons().get(row);
                return switch (col) {
                    case 0 -> season.getSeasonNumber();
                    case 1 -> season.getPremiereYear();
                    case 2 -> season.getEpisodes().size();
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
            episodesButton.setEnabled(rowSelected);
        });

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel,BorderLayout.PAGE_END);
        if (user.isAdmin()) {
            JButton add = new JButton("Add");
            add.addActionListener(e ->{
                new SeasonDialog((JFrame)getParent(),"Add Season",series,null);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            });
            buttonsPanel.add(add);

            edit = new JButton("Edit");
            edit.setEnabled(false);
            edit.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    Season season = series.getSeasons().get(modelRow);
                    new SeasonDialog((JFrame) getParent(),"Edit Season",series, season);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            buttonsPanel.add(edit);

            delete = new JButton("Delete");
            delete.setEnabled(false);
            delete.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    series.getSeasons().remove(modelRow);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            buttonsPanel.add(delete);
        }
        episodesButton = new JButton("View Episodes");
        episodesButton.setEnabled(false);
        episodesButton.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Season season = series.getSeasons().get(modelRow);
                new EpisodeListDialog((JFrame)getParent(),"Episodes",user,season);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        buttonsPanel.add(episodesButton);
    }
}
