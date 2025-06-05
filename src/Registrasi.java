import javax.swing.*;
import java.awt.*;

public class Registrasi extends JFrame {
    private JTextField usernameField, securityAnswerField, addressField, phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> securityQuestionComboBox;

    public Registrasi() {
        setTitle("Buat Akun - CyclePro");
        setSize(450, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Colors.BACKGROUND_SECONDARY);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));


        int xMargin = 20;
        int yPos = 15;
        int labelWidth = 150;
        int fieldWidth = 200;
        int componentHeight = 25;
        int ySpacing = 12;

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        usernameLabel.setForeground(Colors.TEXT_PRIMARY);
        formPanel.add(usernameLabel);
        usernameField = new JTextField(20);
        usernameField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        usernameField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        formPanel.add(usernameField);
        yPos += componentHeight + ySpacing;

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        passwordLabel.setForeground(Colors.TEXT_PRIMARY);
        formPanel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        passwordField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        formPanel.add(passwordField);
        yPos += componentHeight + ySpacing;

        // Security Question
        JLabel securityQuestionLabel = new JLabel("Pertanyaan Keamanan:");
        securityQuestionLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        securityQuestionLabel.setForeground(Colors.TEXT_PRIMARY);
        formPanel.add(securityQuestionLabel);
        String[] questions = {"Siapa nama hewan peliharaan pertama Anda?", "Apa nama SD Anda?", "Di kota mana orang tua Anda bertemu?"}; //
        securityQuestionComboBox = new JComboBox<>(questions);
        securityQuestionComboBox.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        securityQuestionComboBox.setBackground(Color.WHITE); 
        securityQuestionComboBox.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        formPanel.add(securityQuestionComboBox);
        yPos += componentHeight + ySpacing;

        // Security Answer
        JLabel securityAnswerLabel = new JLabel("Jawaban Keamanan:");
        securityAnswerLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        securityAnswerLabel.setForeground(Colors.TEXT_PRIMARY);
        formPanel.add(securityAnswerLabel);
        securityAnswerField = new JTextField(20);
        securityAnswerField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        securityAnswerField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        formPanel.add(securityAnswerField);
        yPos += componentHeight + ySpacing;

        // Address
        JLabel addressLabelText = new JLabel("Alamat:");
        addressLabelText.setBounds(xMargin, yPos, labelWidth, componentHeight);
        addressLabelText.setForeground(Colors.TEXT_PRIMARY);
        formPanel.add(addressLabelText);
        addressField = new JTextField(20);
        addressField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        addressField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        formPanel.add(addressField);
        yPos += componentHeight + ySpacing;

        // Phone Number
        JLabel phoneLabel = new JLabel("Nomor Telepon:");
        phoneLabel.setBounds(xMargin, yPos, labelWidth, componentHeight);
        phoneLabel.setForeground(Colors.TEXT_PRIMARY);
        formPanel.add(phoneLabel);
        phoneField = new JTextField(20);
        phoneField.setBounds(xMargin + labelWidth + 5, yPos, fieldWidth, componentHeight);
        phoneField.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        formPanel.add(phoneField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Colors.BACKGROUND_PRIMARY);
        JButton registerButton = new JButton("Daftar");
        registerButton.setBackground(Colors.BUTTON_SUCCESS_BACKGROUND);
        registerButton.setForeground(Colors.BUTTON_SUCCESS_TEXT);
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));

        JButton backButton = new JButton("Kembali ke Login");
        backButton.setBackground(Colors.BUTTON_SECONDARY_BACKGROUND);
        backButton.setForeground(Colors.BUTTON_SECONDARY_TEXT);
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));

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

            if (DatabaseHelper.registerUser(username, password, securityQuestion, securityAnswer, address, phone)) { //
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