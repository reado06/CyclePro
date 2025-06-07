import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class HalamanLogin extends JFrame {
    private RoundedTextField usernameField;
    private RoundedJPasswordField passwordField;

    public HalamanLogin() {
        setTitle("Login - CyclePro");
        setSize(900, 550); // Ukuran frame disesuaikan dengan desain
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Agar ukuran tidak berubah

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 550));
        add(layeredPane);

        // Background Image Panel
        JLabel backgroundLabel = new JLabel();
        try {
            java.net.URL imgUrl = getClass().getResource("/img/DesainBG.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(900, 550, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("Background image not found: /img/DesainBG.png");
                JPanel fallbackPanel = new JPanel();
                fallbackPanel.setBackground(Colors.BACKGROUND_PRIMARY); 
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

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder());

        int panelWidth = 400;
        int panelHeight = 400;
        int panelX = 900 - panelWidth - 40;
        int panelY = (550 - panelHeight) / 2;
        loginPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
        layeredPane.add(loginPanel, JLayeredPane.PALETTE_LAYER);

        // Components inside Login Panel
        int xMargin = 50;
        int currentY = 50;
        int fieldHeight = 35;
        int spacing = 20;

        // Title "USER LOGIN"
        JLabel titleLabel = new JLabel("USER LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, currentY, panelWidth, 30);
        loginPanel.add(titleLabel);
        currentY += 50;

        // Username Field
        usernameField = new RoundedTextField("Username", 20); // Placeholder "Username"
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(Color.WHITE);
        usernameField.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), fieldHeight);
        loginPanel.add(usernameField);
        addPlaceholderAndFocusListeners(usernameField, "Username", false); // Tambahkan listener fokus
        currentY += fieldHeight + spacing;

        // Password Field
        passwordField = new RoundedJPasswordField("Masukkan Password", 20); // Menggunakan RoundedJPasswordField
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), fieldHeight);
        loginPanel.add(passwordField);
        addPlaceholderAndFocusListeners(passwordField, "Masukkan Password", true); // Tambahkan listener fokus
        currentY += fieldHeight + 20; // Spasi disesuaikan setelah password field

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0xD4AF37));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(10, 25, 10, 25));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBounds(xMargin, currentY, panelWidth - (2 * xMargin), 40);
        loginPanel.add(loginButton);
        currentY += 40 + 20;

        // "Buat Akun" (Register) link - Dipusatkan
        JLabel createAccountLabel = new JLabel("Buat Akun");
        createAccountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        createAccountLabel.setForeground(Colors.TEXT_LINK);
        createAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setBounds(0, currentY, panelWidth, 20);
        loginPanel.add(createAccountLabel);
        createAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Registrasi().setVisible(true);
                dispose();
            }
        });

        // --- Action Listeners for Login Button (Ini yang paling penting!) ---
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validasi placeholder
            if (username.isEmpty() || username.equals("Username") || password.isEmpty() || password.equals("Masukkan Password")) {
                JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Coba login sebagai Admin
            Admin adminUser = DatabaseHelper.authenticateAdmin(username, password);
            if (adminUser != null) {
                Main.currentUser = adminUser;
                JOptionPane.showMessageDialog(this, "Login Admin Berhasil!");
                new AdminDashboard().setVisible(true); // Membuka Admin Dashboard
                dispose();
                return;
            }

            // Jika bukan admin, coba login sebagai User biasa
            User regularUser = DatabaseHelper.authenticateUser(username, password);
            if (regularUser != null) {
                Main.currentUser = regularUser;
                JOptionPane.showMessageDialog(this, "Login Pengguna Berhasil!");
                new Dashboard().setVisible(true); // Membuka Dashboard user biasa
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
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
                if (field instanceof RoundedTextField) {
                    ((RoundedTextField) field).setBorderColor(Colors.BUTTON_PRIMARY_BACKGROUND);
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
                        pf.setEchoChar((char) 0);
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
                if (field instanceof RoundedTextField) {
                    ((RoundedTextField) field).setBorderColor(Colors.BORDER_COLOR);
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
    static class RoundedTextField extends JTextField {
        private Shape shape;
        private int arcWidth = 20;
        private int arcHeight = 20;
        private Color borderColor = Colors.BORDER_COLOR; // Default border color

        public RoundedTextField(int size) {
            super(size);
            setOpaque(false); // Penting agar background kustom terlihat
            setBorder(new EmptyBorder(5, 10, 5, 10)); // Padding internal
        }
        
        // Constructor with initial text, to be used with placeholder setup
        public RoundedTextField(String text, int size) {
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