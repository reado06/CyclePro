import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class Registrasi extends JFrame {
    private RoundedJTextField usernameField;
    private RoundedJPasswordField passwordField;
    private RoundedJTextField addressField;
    private RoundedJTextField phoneField;

    public Registrasi() {
        setTitle("Buat Akun - CyclePro");
        setSize(900, 550); // Ukuran frame disamakan dengan HalamanLogin
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 550));
        add(layeredPane);

        // Background Image Panel
        JLabel backgroundLabel = new JLabel();
        try {
            // Menggunakan DesainBG.png sesuai screenshot terbaru
            java.net.URL imgUrl = getClass().getResource("/img/DesainBG.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(900, 550, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("Background image not found: /img/DesainBG.png");
                JPanel fallbackPanel = new JPanel();
                fallbackPanel.setBackground(Colors.BACKGROUND_PRIMARY); // Fallback warna jika gambar tidak ada
                fallbackPanel.setBounds(0, 0, 900, 550);
                layeredPane.add(fallbackPanel, JLayeredPane.DEFAULT_LAYER);
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.setBackground(Colors.BACKGROUND_PRIMARY); // Fallback warna jika error
            fallbackPanel.setBounds(0, 0, 900, 550);
            layeredPane.add(fallbackPanel, JLayeredPane.DEFAULT_LAYER);
        }
        backgroundLabel.setBounds(0, 0, 900, 550);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Registration Panel
        JPanel registrasiPanel = new JPanel();
        registrasiPanel.setLayout(null);
        registrasiPanel.setBackground(Color.WHITE);
        registrasiPanel.setBorder(BorderFactory.createEmptyBorder());

        // Penyesuaian ukuran dan posisi panel registrasi agar full sampai atas dan ke kanan
        int panelWidth = 400; // Lebar tetap
        int panelHeight = 550; // Tinggi sama dengan tinggi frame agar full vertikal
        int panelX = 900 - panelWidth; // Dimulai dari tepi kanan
        int panelY = 0; // Dimulai dari tepi atas
        
        registrasiPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
        layeredPane.add(registrasiPanel, JLayeredPane.PALETTE_LAYER);

        // Components inside Registration Panel
        int xMargin = 50;
        int currentY = 30; // Posisi Y awal sedikit lebih tinggi
        int fieldHeight = 35;
        int spacing = 15; // Spasi antar field

        // Title "BUAT AKUN"
        JLabel titleLabel = new JLabel("BUAT AKUN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Tengah
        titleLabel.setBounds(0, currentY, panelWidth, 30);
        registrasiPanel.add(titleLabel);
        currentY += 50;

        // Username Field
        usernameField = new RoundedJTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(Color.WHITE);
        usernameField.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), fieldHeight);
        registrasiPanel.add(usernameField);
        addPlaceholderAndFocusListeners(usernameField, "Username", false); // Placeholder
        currentY += fieldHeight + spacing;

        // Password Field
        passwordField = new RoundedJPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), fieldHeight);
        registrasiPanel.add(passwordField);
        addPlaceholderAndFocusListeners(passwordField, "Password", true); // Placeholder
        currentY += fieldHeight + spacing;

        // Address Field
        addressField = new RoundedJTextField(20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        addressField.setBackground(Color.WHITE);
        addressField.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), fieldHeight);
        registrasiPanel.add(addressField);
        addPlaceholderAndFocusListeners(addressField, "Alamat", false); // Placeholder
        currentY += fieldHeight + spacing;

        // Phone Number Field
        phoneField = new RoundedJTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setBackground(Color.WHITE);
        phoneField.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), fieldHeight);
        registrasiPanel.add(phoneField);
        addPlaceholderAndFocusListeners(phoneField, "Nomor Telepon", false); // Placeholder
        currentY += fieldHeight + spacing + 10; // Spasi lebih besar sebelum tombol

        // Register Button
        JButton registerButton = new JButton("Daftar");
        registerButton.setBackground(Colors.BUTTON_SUCCESS_BACKGROUND);
        registerButton.setForeground(Colors.BUTTON_SUCCESS_TEXT);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setFocusPainted(false);
        registerButton.setBorder(new EmptyBorder(10, 25, 10, 25));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), 40);
        registrasiPanel.add(registerButton);
        currentY += 40 + 20;

        // "Kembali ke Login" link (dipusatkan)
        JLabel backToLoginLabel = new JLabel("Kembali ke Login");
        backToLoginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        backToLoginLabel.setForeground(Colors.TEXT_LINK);
        backToLoginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backToLoginLabel.setBounds(0, currentY, panelWidth, 20);
        registrasiPanel.add(backToLoginLabel);

        // Action Listeners
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String address = addressField.getText();
            String phone = phoneField.getText();

            // Validasi placeholder
            if (username.isEmpty() || username.equals("Username") || 
                password.isEmpty() || password.equals("Password") || 
                address.isEmpty() || address.equals("Alamat") || 
                phone.isEmpty() || phone.equals("Nomor Telepon")) 
            {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseHelper.registerUser(username, password, address, phone)) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.");
                new HalamanLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi gagal. Username mungkin sudah ada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new HalamanLogin().setVisible(true);
                dispose();
            }
        });
    }

    // Helper method to add focus listeners for placeholder behavior and border color change
    private void addPlaceholderAndFocusListeners(JTextComponent field, String placeholder, boolean isPasswordField) {
        // Set initial state based on placeholder
        if (isPasswordField) {
            JPasswordField pf = (JPasswordField) field;
            pf.setText(placeholder);
            pf.setEchoChar((char) 0); // Show placeholder text
            pf.setForeground(Colors.TEXT_SECONDARY);
        } else {
            JTextField tf = (JTextField) field;
            tf.setText(placeholder);
            tf.setForeground(Colors.TEXT_SECONDARY);
        }

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPasswordField) {
                    JPasswordField pf = (JPasswordField) field;
                    if (new String(pf.getPassword()).equals(placeholder)) {
                        pf.setText("");
                        pf.setEchoChar('*'); // Sembunyikan karakter password
                        pf.setForeground(Colors.TEXT_PRIMARY);
                    }
                } else {
                    JTextField tf = (JTextField) field;
                    if (tf.getText().equals(placeholder)) {
                        tf.setText("");
                        tf.setForeground(Colors.TEXT_PRIMARY);
                    }
                }
                // Ubah warna border saat fokus
                if (field instanceof RoundedJTextField) {
                    ((RoundedJTextField) field).setBorderColor(Colors.BUTTON_PRIMARY_BACKGROUND);
                } else if (field instanceof RoundedJPasswordField) {
                    ((RoundedJPasswordField) field).setBorderColor(Colors.BUTTON_PRIMARY_BACKGROUND);
                }
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (isPasswordField) {
                    JPasswordField pf = (JPasswordField) field;
                    if (new String(pf.getPassword()).isEmpty()) {
                        pf.setEchoChar((char) 0); // Tampilkan placeholder jika kosong
                        pf.setText(placeholder);
                        pf.setForeground(Colors.TEXT_SECONDARY);
                    }
                } else {
                    JTextField tf = (JTextField) field;
                    if (tf.getText().isEmpty()) {
                        tf.setText(placeholder);
                        tf.setForeground(Colors.TEXT_SECONDARY);
                    }
                }
                // Kembalikan warna border saat fokus hilang
                if (field instanceof RoundedJTextField) {
                    ((RoundedJTextField) field).setBorderColor(Colors.BORDER_COLOR);
                } else if (field instanceof RoundedJPasswordField) {
                    ((RoundedJPasswordField) field).setBorderColor(Colors.BORDER_COLOR);
                }
                field.repaint();
            }
        });
    }

    /**
     * Inner static class for a JTextField with rounded corners and customizable border color.
     */
    static class RoundedJTextField extends JTextField {
        private Shape shape;
        private int arcWidth = 20;
        private int arcHeight = 20;
        private Color borderColor = Colors.BORDER_COLOR; // Default border color

        public RoundedJTextField(int size) {
            super(size);
            setOpaque(false); // Penting agar background kustom terlihat
            setBorder(new EmptyBorder(5, 10, 5, 10)); // Padding internal
        }
        
        // Constructor with initial text, to be used with placeholder setup
        public RoundedJTextField(String text, int size) {
            super(text, size);
            setOpaque(false);
            setBorder(new EmptyBorder(5, 10, 5, 10));
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
            repaint(); // Redraw component to apply new border color
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Menggambar background melengkung
            g2.setColor(getBackground()); // Menggunakan background yang diatur di konstruktor
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
            
            super.paintComponent(g2); // Membiarkan super class menggambar teks
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor); // Warna border yang bisa berubah
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
            }
            return shape.contains(x, y);
        }
    }

    /**
     * Inner static class for a JPasswordField with rounded corners and customizable border color.
     */
    static class RoundedJPasswordField extends JPasswordField {
        private Shape shape;
        private int arcWidth = 20;
        private int arcHeight = 20;
        private Color borderColor = Colors.BORDER_COLOR; // Default border color

        public RoundedJPasswordField(int size) {
            super(size);
            setOpaque(false); // Penting agar background kustom terlihat
            setBorder(new EmptyBorder(5, 10, 5, 10)); // Padding internal
        }

        // Constructor with initial text, to be used with placeholder setup
        public RoundedJPasswordField(String text, int size) {
            super(text, size);
            setOpaque(false);
            setBorder(new EmptyBorder(5, 10, 5, 10));
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
            repaint(); // Redraw component to apply new border color
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Menggambar background melengkung
            g2.setColor(getBackground()); // Menggunakan background yang diatur di konstruktor
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
            
            super.paintComponent(g2); // Membiarkan super class menggambar teks (termasuk placeholder atau *echo char*)
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor); // Warna border yang bisa berubah
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
            }
            return shape.contains(x, y);
        }
    }
}