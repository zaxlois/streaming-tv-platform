package gui;

import api.Data;
import api.Media.Content;
import api.Media.Series;
import api.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class SeriesListDialog extends JDialog {
    private JDialog dialog;
    private Data data;
    private JTable table;
    private JButton edit;
    private JButton delete;
    private JButton preview;

    public SeriesListDialog(JFrame parent, Data data, User user) {
        dialog = new JDialog(parent, "List of Series", true);
        this.data = data;
        dialog.setLocation(parent.getX()+100, parent.getY()+100);
        buildUI(user);
        dialog.pack();
        dialog.setVisible(true);
    }

    public SeriesListDialog(JFrame parent, Data data, Content media) {
        dialog = new JDialog(parent, "List of Series", true);
        this.data = data;
        dialog.setLocation(parent.getX()+100, parent.getY()+100);
        buildUI(media);
        dialog.pack();
        dialog.setVisible(true);
    }


        final private void buildUI(User user) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Title","Category","Age Restriction","Stars"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return data.getSeries().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Series series = data.getSeries().get(row);
                switch (col) {
                    case 0: return series.getTitle();
                    case 1: return series.getCategory();
                    case 2: return series.getAgeRestriction();
                    case 3: return series.getStars();
                    default: return null;
                }
            }
        });
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);


        JPanel panel = new JPanel();
        edit = new JButton("Edit");
        delete = new JButton("Delete");

        if (user.isAdmin()) {
            edit.setEnabled(false);
            edit.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    Series series = data.getSeries().get(modelRow);
                    new SeriesDialog((JFrame) dialog.getParent(), data, "Edit Series", series,user);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            panel.add(edit);

            delete.setEnabled(false);
            delete.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    data.deleteFromDatabase(data.getSeries().get(modelRow));;
                    data.getSeries().remove(modelRow);
                    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                }
            });
            panel.add(delete);
        }
        preview = new JButton("Preview");
        preview.setEnabled(false);
        preview.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Series series = data.getSeries().get(modelRow);
                new Preview((JFrame)dialog.getParent(),data,"Preview Series",series,user);
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });
        panel.add(preview);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            edit.setEnabled(rowSelected);
            delete.setEnabled(rowSelected);
            preview.setEnabled(rowSelected);
        });

        dialog.add(panel, BorderLayout.PAGE_END);
    }

    private void buildUI(Content media) {
        table = new JTable(new AbstractTableModel() {

            String columnNames[] = {"Title","Category","Age Restriction","Stars"};

            public String getColumnName(int col) {
                return columnNames[col];
            }
            public int getRowCount() { return data.getSeries().size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                Series series = data.getSeries().get(row);
                switch (col) {
                    case 0: return series.getTitle();
                    case 1: return series.getCategory();
                    case 2: return series.getAgeRestriction();
                    case 3: return series.getStars();
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
                Series series = data.getSeries().get(modelRow);
                if ( media.addMediaToSimilar(series) ) {
                    JOptionPane.showMessageDialog(dialog.getParent(),"Added successfully","Success",JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog.getParent(), "This series already exists!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(add);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            if (rowSelected) {
                boolean same = !media.equals(data.getSeries().get(table.convertRowIndexToModel(table.getSelectedRow())));
                add.setEnabled(same);
            }
        });
    }
}
