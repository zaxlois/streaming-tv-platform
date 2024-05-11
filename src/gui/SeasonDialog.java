package gui;

import api.Media.Season;
import api.Media.Series;

import javax.swing.*;
import java.awt.*;

public class SeasonDialog extends JDialog{
    private Series series;
    private Season season;
    public SeasonDialog(JFrame parent, String title, Series series, Season season) {
        super(parent,title,true);
        if (season == null) {
            setLocation(parent.getX() + 100, parent.getY() + 100);
        } else {
            setLocation(parent.getX() + 200, parent.getY() + 200);
        }
        this.series = series;
        this.season = season;
        buildUI();
        pack();
        setVisible(true);
    }

    private void buildUI() {
        JPanel panelData = new JPanel();
        add(panelData, BorderLayout.CENTER);
        panelData.setLayout(new BorderLayout());

        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new GridLayout(0,1));
        panelData.add(panelLabels, BorderLayout.LINE_START);

        String[] fields = {"Season Number:", "Premiere Year:"};
        for (String field : fields) {
            JLabel label = new JLabel(field);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            panelLabels.add(label);
        }

        JPanel panelTextFields = new JPanel();
        panelTextFields.setLayout(new GridLayout(0,1));
        panelData.add(panelTextFields, BorderLayout.CENTER);

        JTextField seasonNumber = new JTextField(15);
        panelTextFields.add(seasonNumber);

        JTextField seasonYear = new JTextField(15);
        panelTextFields.add(seasonYear);

        if (season != null) {
            seasonNumber.setText(String.valueOf(season.getSeasonNumber()));
            seasonYear.setText(String.valueOf(season.getPremiereYear()));
        }

        JPanel panelButtons = new JPanel();

        JButton buttonOk = new JButton("Ok");
        buttonOk.addActionListener(e -> {
            if (seasonNumber.getText().isEmpty() || seasonYear.getText().isEmpty()) {
                JOptionPane.showMessageDialog(SeasonDialog.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                if (season == null) {
                    season = new Season(Integer.parseInt(seasonNumber.getText()), Integer.parseInt(seasonYear.getText()));
                    series.addSeason(season);
                } else {
                    season.setSeasonNumber(Integer.parseInt(seasonNumber.getText()));
                    season.setPremiereYear(Integer.parseInt(seasonYear.getText()));
                }
                dispose();
            }
        });
        panelButtons.add(buttonOk);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> dispose());
        panelButtons.add(buttonCancel);

        add(panelButtons,BorderLayout.PAGE_END);
    }
}
