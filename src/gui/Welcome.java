package gui;

import api.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Welcome extends JFrame {
    private Data data;
    public Welcome(Data data) {
        setTitle("Welcome to FlixTV");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(600,600));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        buildUI();
        setVisible(true);
        this.data = data;
    }

    private void buildUI() {
        JPanel Main = new JPanel(new GridLayout(3,1));
        Main.setBackground(Color.BLACK);

        JLabel welcomeMessage = new JLabel("Welcome to FlixTV");
        welcomeMessage.setFont(new Font(null,Font.BOLD,28));
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        Main.add(welcomeMessage);

        JPanel signInPanel = new JPanel(new BorderLayout());
        signInPanel.setBackground(Color.BLACK);
        signInPanel.setBorder(BorderFactory.createEmptyBorder(50,50,20,50));

        JLabel signInLabel = new JLabel("Already have an account?");
        signInLabel.setForeground(Color.WHITE);
        signInLabel.setFont(new Font(null,Font.BOLD,23));
        signInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signInLabel.setVerticalTextPosition(SwingConstants.CENTER);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBackground(Color.WHITE);
        signInButton.setForeground(Color.BLACK);
        signInButton.setFont(new Font(null,Font.BOLD,15));
        signInButton.setFocusable(false);
        signInButton.addActionListener(e -> {
            new Login(data);
            dispose();
        });

        signInPanel.add(signInLabel,BorderLayout.CENTER);
        signInPanel.add(signInButton,BorderLayout.EAST);

        Main.add(signInPanel);

        JPanel registerPanel = new JPanel(new BorderLayout());
        registerPanel.setBackground(Color.BLACK);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20,50,50,50));

        JLabel registerLabel = new JLabel("New to FlixTV?");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setFont(new Font(null, Font.BOLD, 23));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setVerticalTextPosition(SwingConstants.CENTER);

        JButton registerButton = new JButton("Register Now");
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font(null, Font.BOLD, 15));
        registerButton.setFocusable(false);
        registerButton.addActionListener(e -> {
            new Register(data);
            dispose();
        });

        registerPanel.add(registerLabel,BorderLayout.CENTER);
        registerPanel.add(registerButton,BorderLayout.EAST);

        Main.add(registerPanel);

        add(Main,BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                data.save();
            }
        });
    }
}
