package gui;

import api.Data;
import api.Media.Content;
import api.Media.Movie;
import api.Media.Series;
import api.Search;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class ResultsListDialog extends JDialog{
    private JTable table;
    private JButton preview;
    private JButton edit;
    private JButton delete;
    public ResultsListDialog(JFrame parent, Search searchContent, User user, Data data) {
        super(parent,"results",true);
        setLocation(parent.getX() + 300, parent.getY() + 300);
        buildUI(searchContent,user,data);
        pack();
        setVisible(true);
    }

    private void buildUI(Search search,User user,Data data) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Title","Type","Category","Age Restriction","Stars"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return search.results().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Content media = search.results().get(row);
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

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            edit.setEnabled(rowSelected);
            delete.setEnabled(rowSelected);
            preview.setEnabled(rowSelected);
        });
        JPanel panel = new JPanel();

        edit = new JButton("Edit");
        edit.setEnabled(false);
        edit.setVisible(false);
        edit.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Content media = search.results().get(modelRow);
                if (media.getType().equals("Movie")) {
                    new MovieDialog((JFrame) getParent(), data, "Edit Movie", (Movie) media,user);
                } else {
                    new SeriesDialog((JFrame) getParent(),data,"Edit Series",(Series) media,user);
                }
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        panel.add(edit);

        delete = new JButton("Delete");
        delete.setEnabled(false);
        delete.setVisible(false);
        delete.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Content media = search.results().get(modelRow);
                if (media.getType().equals("Movie")) {
                    data.deleteFromDatabase(data.getMovies().get(modelRow));
                    data.getMovies().remove(modelRow);
                } else {
                    data.deleteFromDatabase(data.getSeries().get(modelRow));
                    data.getSeries().remove(modelRow);
                }
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        panel.add(delete);

        preview = new JButton("Preview");
        preview.setEnabled(false);
        preview.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Content media = search.results().get(modelRow);
                if (media.getType().equals("Movie")) {
                    new Preview((JFrame)getParent(),data,"Preview Movie", (Movie) media,user);
                } else {
                    new Preview((JFrame)getParent(),data,"Preview Series", (Series) media,user);
                }
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        panel.add(preview);

        if (user.isAdmin()) {
            edit.setVisible(true);
            delete.setVisible(true);
        }

        add(panel, BorderLayout.PAGE_END);
    }
}
