import javax.swing.*;
import java.awt.*;

public class HalamanLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public HalamanLogin() {
        setTitle("Login - CyclePro");
        setSize(400, 330); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Colors.BACKGROUND_PRIMARY); 
        JLabel titleLabel = new JLabel("SELAMAT DATANG DI CYCLEPRO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Colors.TEXT_PRIMARY); 
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(Colors.BACKGROUND_SECONDARY); 
        inputPanel.setPreferredSize(new Dimension(350, 100));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 80, 25);
        usernameLabel.setForeground(Colors.TEXT_PRIMARY);
        inputPanel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(110, 20, 220, 25);
        usernameField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        inputPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 55, 80, 25);
        passwordLabel.setForeground(Colors.TEXT_PRIMARY);
        inputPanel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(110, 55, 220, 25);
        passwordField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        inputPanel.add(passwordField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Colors.BACKGROUND_PRIMARY); 

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Colors.BUTTON_PRIMARY_BACKGROUND);
        loginButton.setForeground(Colors.BUTTON_PRIMARY_TEXT);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));

        JButton registerButton = new JButton("Buat Akun");
        registerButton.setBackground(Colors.BUTTON_SUCCESS_BACKGROUND);
        registerButton.setForeground(Colors.BUTTON_SUCCESS_TEXT);
        registerButton.setFont(new Font("Arial", Font.PLAIN, 12));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = DatabaseHelper.authenticateUser(username, password);
            if (user != null) {
                Main.currentUser = user;
                JOptionPane.showMessageDialog(this, "Login Berhasil!");
                new Dashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new Registrasi().setVisible(true); 
            dispose();
        });
    }
}