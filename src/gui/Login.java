package gui;

import api.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame {
    private Data data;
    public Login(Data data) {
        setTitle("Sign In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(600,600));
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        buildUI();
        setVisible(true);
        this.data = data;
    }

    private void buildUI() {
        JPanel info = new JPanel();
        info.setBackground(Color.BLACK);

        JLabel infoMessage = new JLabel("Sign In");
        infoMessage.setFont(new Font(null,Font.BOLD,28));
        infoMessage.setForeground(Color.WHITE);
        infoMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoMessage.setHorizontalAlignment(SwingConstants.CENTER);
        info.add(infoMessage);

        add(info,BorderLayout.PAGE_START);

        JPanel Main = new JPanel(null);
        Main.setBackground(Color.BLACK);

        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setFont(new Font(null,Font.BOLD,17));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        usernameLabel.setBounds(100,90,120,80);
        Main.add(usernameLabel);

        JTextField username = new JTextField(25);
        username.setBounds(230,100,280,60);
        Main.add(username);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setFont(new Font(null,Font.BOLD,17));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setVerticalAlignment(SwingConstants.CENTER);
        passwordLabel.setBounds(100,240,120,80);
        Main.add(passwordLabel);

        JPasswordField password = new JPasswordField(25);
        password.setBounds(230,250,280,60);
        Main.add(password);

        add(Main,BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.setBackground(Color.WHITE);

        JButton login = new JButton("Sign In");
        login.setBackground(Color.WHITE);
        login.setForeground(Color.BLACK);
        login.setFont(new Font(null,Font.BOLD,16));
        login.setFocusable(false);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = username.getText();
                char [] passwordChars = password.getPassword();
                String pass = new String(passwordChars);
                if (id.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    if (data.checkLoginInfo(id,pass)) {
                        JOptionPane.showMessageDialog(Login.this, "Welcome, " + id,"Welcome", JOptionPane.PLAIN_MESSAGE);
                        new Home(data,data.getUser(id));
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Wrong username or password. Try Again!","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton welcome = new JButton("Go Back");
        welcome.setBackground(Color.WHITE);
        welcome.setForeground(Color.BLACK);
        welcome.setFont(new Font(null,Font.BOLD,16));
        welcome.setFocusable(false);
        welcome.addActionListener(e -> {
            new Welcome(data);
            dispose();
        });

        buttonsPanel.add(login);
        buttonsPanel.add(welcome);

        add(buttonsPanel,BorderLayout.PAGE_END);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                data.save();
            }
        });
    }
}
