import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HalamanLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public HalamanLogin() {
        setTitle("Login - CyclePro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Padding antar komponen

        // Panel judul
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("SELAMAT DATANG DI CYCLEPRO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding antar sel
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        usernameField = new JTextField(20);
        inputPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        passwordField = new JPasswordField(20);
        inputPanel.add(passwordField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loginButton = new JButton("Login");
        JButton forgotPasswordButton = new JButton("Lupa Password");
        JButton registerButton = new JButton("Buat Akun");

        buttonPanel.add(loginButton);
        buttonPanel.add(forgotPasswordButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = DatabaseHelper.authenticateUser(username, password);
            if (user != null) {
                Main.currentUser = user; // Simpan user yang login
                JOptionPane.showMessageDialog(this, "Login Berhasil!");
                new Dashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        forgotPasswordButton.addActionListener(e -> {
            new LupaPassword().setVisible(true);
            // dispose(); // Jangan dispose halaman login jika hanya membuka lupa password
        });

        registerButton.addActionListener(e -> {
            new Registrasi().setVisible(true);
            dispose();
        });
    }
}
