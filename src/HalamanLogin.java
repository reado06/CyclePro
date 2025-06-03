import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HalamanLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public HalamanLogin() {
        setTitle("Login - CyclePro");
        setSize(400, 300); // Mungkin perlu disesuaikan setelah perubahan layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel judul
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("SELAMAT DATANG DI CYCLEPRO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null); // Menggunakan null layout
        // Estimasi ukuran panel yang dibutuhkan
        inputPanel.setPreferredSize(new Dimension(350, 100));


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 80, 25); // x, y, width, height
        inputPanel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(110, 20, 220, 25);
        inputPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 55, 80, 25);
        inputPanel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(110, 55, 220, 25);
        inputPanel.add(passwordField);

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