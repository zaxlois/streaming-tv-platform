package gui;

import api.Data;
import api.Media.Content;
import api.Media.Movie;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class MovieListDialog {
    private Content media;
    private JDialog dialog;
    private Data data;
    private JTable table;
    private JButton edit;
    private JButton delete;
    private JButton preview;

    public MovieListDialog(JFrame parent, Data data, User user) {
        dialog = new JDialog(parent, "List of Movies", true);
        this.data = data;
        dialog.setLocation(parent.getX()+100, parent.getY()+100);
        buildUI(user);
        dialog.pack();
        dialog.setVisible(true);
    }
    public MovieListDialog(JFrame parent, Data data, Content media) {
        dialog = new JDialog(parent, "List of Movies", true);
        dialog.setLocation(parent.getX()+100, parent.getY()+100);
        this.data = data;
        buildUI(media);
        dialog.pack();
        dialog.setVisible(true);
    }

    final private void buildUI(User user) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Title", "Category", "Age Restriction", "Duration", "Stars"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return data.getMovies().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Movie movie = data.getMovies().get(row);
                switch (col) {
                     case 0: return movie.getTitle();
                     case 1: return movie.getCategory();
                     case 2: return movie.getAgeRestriction();
                     case 3: return movie.getDuration();
                     case 4: return movie.getStars();
                    default: return null;
                }
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);
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
                Movie movie = data.getMovies().get(modelRow);
                new MovieDialog((JFrame) dialog.getParent(), data, "Edit Movie", movie,user);
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
                data.deleteFromDatabase(data.getMovies().get(modelRow));
                data.getMovies().remove(modelRow);
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
                Movie movie = data.getMovies().get(modelRow);
                new Preview((JFrame)dialog.getParent(),data,"Preview Movie",movie,user);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        panel.add(preview);

        if (user.isAdmin()) {
            edit.setVisible(true);
            delete.setVisible(true);
        }

        dialog.add(panel, BorderLayout.PAGE_END);
    }

    private void buildUI(Content media) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Title", "Category", "Age Restriction", "Duration", "Stars"};
            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return data.getMovies().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Movie movie = data.getMovies().get(row);
                switch (col) {
                    case 0: return movie.getTitle();
                    case 1: return movie.getCategory();
                    case 2: return movie.getAgeRestriction();
                    case 3: return movie.getDuration();
                    case 4: return movie.getStars();
                    default: return null;
                }
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        dialog.add(panel,BorderLayout.PAGE_END);

        JButton add = new JButton("Add");
        add.setEnabled(false);
        add.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Movie movie = data.getMovies().get(modelRow);
                if ( media.addMediaToSimilar(movie) ) {
                    JOptionPane.showMessageDialog(dialog.getParent(),"Added successfully","Success",JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog.getParent(), "This movie already exists!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(add);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            if (rowSelected) {
                boolean same = !media.equals(data.getMovies().get(table.convertRowIndexToModel(table.getSelectedRow())));
                add.setEnabled(same);
            }
        });
    }
}
