package gui;

import api.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Register extends JFrame {
    private Data data;
    public Register(Data data) {
        setTitle("Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(600,600));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        buildUI();
        setVisible(true);
        this.data = data;
    }

    private void buildUI() {
        JPanel info = new JPanel();
        info.setBackground(Color.BLACK);

        JLabel infoMessage = new JLabel("Register");
        infoMessage.setFont(new Font(null,Font.BOLD,28));
        infoMessage.setForeground(Color.WHITE);
        infoMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoMessage.setHorizontalAlignment(SwingConstants.CENTER);
        info.add(infoMessage);

        add(info,BorderLayout.PAGE_START);

        JPanel Main = new JPanel(null);
        Main.setBackground(Color.BLACK);

        JLabel firstNameLabel = new JLabel("First Name : ");
        firstNameLabel.setFont(new Font(null,Font.BOLD,17));
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        firstNameLabel.setBounds(100,60,120,80);
        Main.add(firstNameLabel);

        JTextField firstName = new JTextField(25);
        firstName.setBounds(230,70,280,60);
        Main.add(firstName);

        JLabel lastNameLabel = new JLabel("Last Name : ");
        lastNameLabel.setFont(new Font(null,Font.BOLD,17));
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lastNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        lastNameLabel.setBounds(100,140,120,80);
        Main.add(lastNameLabel);

        JTextField lastName = new JTextField(25);
        lastName.setBounds(230,150,280,60);
        Main.add(lastName);

        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setFont(new Font(null,Font.BOLD,17));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        usernameLabel.setBounds(100,220,120,80);
        Main.add(usernameLabel);

        JTextField username = new JTextField(25);
        username.setBounds(230,230,280,60);
        Main.add(username);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setFont(new Font(null,Font.BOLD,17));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setVerticalAlignment(SwingConstants.CENTER);
        passwordLabel.setBounds(100,300,120,80);
        Main.add(passwordLabel);

        JPasswordField password = new JPasswordField(25);
        password.setBounds(230,310,280,60);
        Main.add(password);

        add(Main,BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.setBackground(Color.WHITE);

        JButton register = new JButton("Register");
        register.setBackground(Color.WHITE);
        register.setForeground(Color.BLACK);
        register.setFont(new Font(null,Font.BOLD,16));
        register.setFocusable(false);
        register.addActionListener(e -> {
            String fName = firstName.getText();
            String lName = lastName.getText();
            String id = username.getText();
            char [] passwordChars = password.getPassword();
            String pass = new String(passwordChars);
            if (id.isEmpty() || pass.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
                JOptionPane.showMessageDialog(Register.this, "All fields must be filled!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                if (data.addUser(fName,lName,id,pass)) {
                    JOptionPane.showMessageDialog(Register.this,"Registration successful","Success",JOptionPane.PLAIN_MESSAGE);
                    new Home(data,data.getUser(id));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Register.this,"This username already exists. Pick another one!","Error",JOptionPane.ERROR_MESSAGE);
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

        buttonsPanel.add(register);
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
