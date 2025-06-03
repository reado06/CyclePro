import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrasi extends JFrame {
    private JTextField usernameField, securityAnswerField, addressField, phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> securityQuestionComboBox;

    public Registrasi() {
        setTitle("Buat Akun - CyclePro");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Agar tidak menutup seluruh aplikasi
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; usernameField = new JTextField(20); formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; passwordField = new JPasswordField(20); formPanel.add(passwordField, gbc);

        // Security Question
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Pertanyaan Keamanan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        String[] questions = {"Siapa nama hewan peliharaan pertama Anda?", "Apa nama SD Anda?", "Di kota mana orang tua Anda bertemu?"};
        securityQuestionComboBox = new JComboBox<>(questions);
        formPanel.add(securityQuestionComboBox, gbc);

        // Security Answer
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Jawaban Keamanan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; securityAnswerField = new JTextField(20); formPanel.add(securityAnswerField, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; addressField = new JTextField(20); formPanel.add(addressField, gbc);

        // Phone Number
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("Nomor Telepon:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; phoneField = new JTextField(20); formPanel.add(phoneField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("Daftar");
        JButton backButton = new JButton("Kembali ke Login");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String securityQuestion = (String) securityQuestionComboBox.getSelectedItem();
            String securityAnswer = securityAnswerField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();

            if (username.isEmpty() || password.isEmpty() || securityAnswer.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseHelper.registerUser(username, password, securityQuestion, securityAnswer, address, phone)) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.");
                new HalamanLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi gagal. Username mungkin sudah ada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new HalamanLogin().setVisible(true);
            dispose();
        });
    }
}
