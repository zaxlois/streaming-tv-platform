package gui;

import api.Media.Episode;
import api.Media.Season;

import javax.swing.*;
import java.awt.*;

public class EpisodeDialog extends JDialog{
    private Season season;
    private Episode episode;
    public EpisodeDialog(JFrame parent, String title, Season season, Episode episode) {
        super(parent,title,true);
        if (episode == null) {
            setLocation(parent.getX() + 100, parent.getY() + 100);
        } else {
            setLocation(parent.getX() + 200, parent.getY() + 200);
        }
        this.season = season;
        this.episode = episode;
        buildUI();
        pack();
        setVisible(true);
    }

    private void buildUI() {
        JPanel panelData = new JPanel();
        add(panelData, BorderLayout.CENTER);
        panelData.setLayout(new BorderLayout());

        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panelData.add(durationLabel,BorderLayout.LINE_START);

        JTextField duration = new JTextField(15);
        panelData.add(duration,BorderLayout.CENTER);

        if (episode != null) {
            duration.setText(String.valueOf(episode.getDuration()));
        }

        JPanel panelButtons = new JPanel();

        JButton buttonOk = new JButton("Ok");
        buttonOk.addActionListener(e -> {
            if (duration.getText().isEmpty()) {
                JOptionPane.showMessageDialog(EpisodeDialog.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                if (episode == null) {
                    episode = new Episode(Integer.parseInt(duration.getText()));
                    season.addEpisode(episode);
                } else {
                    episode.setDuration(Integer.parseInt(duration.getText()));
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
