import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LupaPassword extends JFrame {
    private JTextField usernameField, securityAnswerField;
    private JLabel questionLabel; // Untuk menampilkan pertanyaan keamanan

    public LupaPassword() {
        setTitle("Lupa Password - CyclePro");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; usernameField = new JTextField(20); panel.add(usernameField, gbc);

        // Tombol untuk mengambil pertanyaan keamanan
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton fetchQuestionButton = new JButton("Dapatkan Pertanyaan Keamanan");
        panel.add(fetchQuestionButton, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Label Pertanyaan Keamanan
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        questionLabel = new JLabel("Pertanyaan keamanan akan muncul di sini.");
        questionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(questionLabel, gbc);
        gbc.gridwidth = 1;

        // Security Answer
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Jawaban Keamanan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; securityAnswerField = new JTextField(20); panel.add(securityAnswerField, gbc);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton recoverButton = new JButton("Pulihkan Password");
        JButton backButton = new JButton("Kembali ke Login");
        buttonPanel.add(recoverButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        fetchQuestionButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan username terlebih dahulu.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String question = DatabaseHelper.getSecurityQuestion(username);
            if (question != null) {
                questionLabel.setText("Pertanyaan: " + question);
            } else {
                questionLabel.setText("Username tidak ditemukan atau tidak ada pertanyaan keamanan.");
            }
        });

        recoverButton.addActionListener(e -> {
            String username = usernameField.getText();
            String answer = securityAnswerField.getText();

            if (username.isEmpty() || answer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan jawaban tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] result = DatabaseHelper.recoverPassword(username, answer);
            if (result != null) {
                String password = result[0];
                JOptionPane.showMessageDialog(this, "Password Anda adalah: " + password, "Password Ditemukan", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Kembali ke login atau tutup saja
            } else {
                JOptionPane.showMessageDialog(this, "Username atau jawaban salah.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            // Jika LoginPage masih ada, tidak perlu buat baru. Tapi untuk simpel, kita buat baru.
            // new LoginPage().setVisible(true);
            dispose();
        });
    }
}
