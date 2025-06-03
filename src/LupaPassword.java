import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LupaPassword extends JFrame {
    private JTextField usernameField, securityAnswerField;
    private JLabel questionLabel;
    private JButton fetchQuestionButton;

    public LupaPassword() {
        setTitle("Lupa Password - CyclePro");
        setSize(450, 300); // Ukuran frame mungkin perlu disesuaikan
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JPanel panel = new JPanel();
        panel.setLayout(null);

        int xMargin = 20;
        int yPos = 20;
        int labelWidth = 150;
        int fieldWidth = 200;
        int componentHeight = 25;
        int ySpacing = 10;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        panel.add(usernameField);
        yPos += componentHeight + ySpacing;

        fetchQuestionButton = new JButton("Dapatkan Pertanyaan Keamanan");
        fetchQuestionButton.setBounds(xMargin, yPos, labelWidth + 5 + fieldWidth, componentHeight); // Tombol lebar
        panel.add(fetchQuestionButton);
        yPos += componentHeight + ySpacing;

        questionLabel = new JLabel("Pertanyaan keamanan akan muncul di sini.");
        questionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        questionLabel.setBounds(xMargin, yPos, labelWidth + 5 + fieldWidth, componentHeight);
        panel.add(questionLabel);
        yPos += componentHeight + ySpacing;

        JLabel securityAnswerLabel = new JLabel("Jawaban Keamanan:");
        securityAnswerLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        panel.add(securityAnswerLabel);

        securityAnswerField = new JTextField(20);
        securityAnswerField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        panel.add(securityAnswerField);

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
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau jawaban salah.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
        });
    }
}