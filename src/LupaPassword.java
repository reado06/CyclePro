import javax.swing.*;
import java.awt.*;

public class LupaPassword extends JFrame {
    private JTextField usernameField, securityAnswerField;
    private JLabel questionLabel; 
    private JButton fetchQuestionButton;

    public LupaPassword() {
        setTitle("Lupa Password - CyclePro");
        setSize(450, 330); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel panel = new JPanel();
        panel.setLayout(null); 
        panel.setBackground(Colors.BACKGROUND_SECONDARY);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        int xMargin = 20;
        int yPos = 20;
        int labelWidth = 150;
        int fieldWidth = 200;
        int componentHeight = 25;
        int ySpacing = 10;

        JLabel usernameLabelText = new JLabel("Username:");
        usernameLabelText.setBounds(xMargin, yPos, labelWidth, componentHeight);
        usernameLabelText.setForeground(Colors.TEXT_PRIMARY);
        panel.add(usernameLabelText);

        usernameField = new JTextField(20);
        usernameField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        usernameField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        panel.add(usernameField);
        yPos += componentHeight + ySpacing;

        fetchQuestionButton = new JButton("Dapatkan Pertanyaan Keamanan");
        fetchQuestionButton.setBounds(xMargin, yPos, labelWidth + 5 + fieldWidth, componentHeight + 5);
        fetchQuestionButton.setBackground(Colors.BUTTON_PRIMARY_BACKGROUND);
        fetchQuestionButton.setForeground(Colors.BUTTON_PRIMARY_TEXT);
        fetchQuestionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(fetchQuestionButton);
        yPos += componentHeight + 5 + ySpacing;

        questionLabel = new JLabel("Pertanyaan keamanan akan muncul di sini."); 
        questionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        questionLabel.setBounds(xMargin, yPos, labelWidth + 5 + fieldWidth, componentHeight);
        questionLabel.setForeground(Colors.TEXT_SECONDARY);
        panel.add(questionLabel);
        yPos += componentHeight + ySpacing;

        JLabel securityAnswerLabelText = new JLabel("Jawaban Keamanan:");
        securityAnswerLabelText.setBounds(xMargin, yPos, labelWidth, componentHeight);
        securityAnswerLabelText.setForeground(Colors.TEXT_PRIMARY);
        panel.add(securityAnswerLabelText);

        securityAnswerField = new JTextField(20);
        securityAnswerField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        securityAnswerField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        panel.add(securityAnswerField);
        yPos += componentHeight + ySpacing + 10;

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Colors.BACKGROUND_PRIMARY);
        JButton recoverButton = new JButton("Pulihkan Password");
        recoverButton.setBackground(Colors.BUTTON_SUCCESS_BACKGROUND);
        recoverButton.setForeground(Colors.BUTTON_SUCCESS_TEXT);
        recoverButton.setFont(new Font("Arial", Font.BOLD, 12));

        JButton backButton = new JButton("Kembali ke Login");
        backButton.setBackground(Colors.BUTTON_SECONDARY_BACKGROUND);
        backButton.setForeground(Colors.BUTTON_SECONDARY_TEXT);
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));

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
                questionLabel.setForeground(Colors.TEXT_PRIMARY); 
            } else {
                questionLabel.setText("Username tidak ditemukan atau tidak ada pertanyaan keamanan.");
                questionLabel.setForeground(Colors.BUTTON_DANGER_BACKGROUND); 
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