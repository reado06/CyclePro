import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Dashboard - CyclePro");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Colors.NAVBAR_BACKGROUND);
        headerPanel.setLayout(new BorderLayout(15, 0));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel appTitleLabel = new JLabel("CYCLEPRO");
        appTitleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        appTitleLabel.setForeground(Colors.NAVBAR_TEXT);
        headerPanel.add(appTitleLabel, BorderLayout.WEST);

        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userInfoPanel.setOpaque(false);

        JButton historyButton = new JButton("Riwayat Pesanan");
        historyButton.setBackground(Colors.BUTTON_PRIMARY_BACKGROUND);
        historyButton.setForeground(Colors.BUTTON_PRIMARY_TEXT);
        historyButton.setFont(new Font("Arial", Font.BOLD, 12));
        historyButton.setFocusPainted(false);
        historyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        historyButton.addActionListener(e -> {
            if (Main.currentUser != null) {
                new HalamanRiwayatPesanan().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Silakan login untuk melihat riwayat pesanan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
        userInfoPanel.add(historyButton);

        String username = (Main.currentUser != null) ? Main.currentUser.getUsername() : "Guest";
        JLabel profileLabel = new JLabel("Halo, " + username + "!");
        profileLabel.setForeground(Colors.NAVBAR_TEXT);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userInfoPanel.add(profileLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Colors.BUTTON_DANGER_BACKGROUND);
        logoutButton.setForeground(Colors.BUTTON_DANGER_TEXT);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            Main.currentUser = null;
            new HalamanLogin().setVisible(true);
            dispose();
        });
        userInfoPanel.add(logoutButton);
        headerPanel.add(userInfoPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Colors.BACKGROUND_PRIMARY);
        mainContentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel chooseCategoryLabel = new JLabel("Pilih Kategori Sepeda:");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        chooseCategoryLabel.setForeground(Colors.TEXT_PRIMARY);
        mainContentPanel.add(chooseCategoryLabel, BorderLayout.NORTH);

        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        categoriesPanel.setBackground(Colors.BACKGROUND_PRIMARY);
        categoriesPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        String[] bikeTypes = {"Sepeda BMX", "Sepeda Gunung", "Sepeda Lipat"};
        String[] categoriesDB = {"BMX", "Gunung", "Lipat"};
        String[] imagePaths = {
            "/img/imgDashboard/sepedaBMX.png",
            "/img/imgDashboard/sepedaGunung.png",
            "/img/imgDashboard/sepedaLIPAT.png"
        };

        for (int i = 0; i < bikeTypes.length; i++) {
            final String categoryName = bikeTypes[i];
            final String categoryDBName = categoriesDB[i];
            final String imagePath = imagePaths[i];

            JPanel categoryItemPanel = new JPanel(new BorderLayout(20, 0));
            categoryItemPanel.setBackground(Colors.BACKGROUND_SECONDARY);
            
            Border defaultBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            );
            categoryItemPanel.setBorder(defaultBorder);

            categoryItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            JLabel imageLabel = new JLabel();
            imageLabel.setPreferredSize(new Dimension(120, 120));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            try {
                java.net.URL imgUrl = getClass().getResource(imagePath);
                if (imgUrl != null) {
                    ImageIcon bikeIcon = new ImageIcon(imgUrl);
                    Image image = bikeIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                } else {
                    System.err.println("Gambar kategori tidak ditemukan: " + imagePath);
                    imageLabel.setText("Gambar Tidak Ditemukan");
                    imageLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    imageLabel.setForeground(Color.RED);
                }
            } catch (Exception e) {
                System.err.println("Error memuat gambar kategori: " + imagePath + " - " + e.getMessage());
                imageLabel.setText("Gagal Muat Gambar");
                imageLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                imageLabel.setForeground(Color.RED);
            }
            categoryItemPanel.add(imageLabel, BorderLayout.WEST);

            JLabel categoryLabelText = new JLabel(categoryName);
            categoryLabelText.setFont(new Font("Arial", Font.BOLD, 22));
            categoryLabelText.setForeground(Colors.TEXT_PRIMARY);
            categoryItemPanel.add(categoryLabelText, BorderLayout.CENTER);

            categoryItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            categoryItemPanel.addMouseListener(new MouseAdapter() {
                Color originalTextForeground = categoryLabelText.getForeground();

                @Override
                public void mouseClicked(MouseEvent e) {
                    new KategoriSepeda(categoryDBName).setVisible(true);
                    dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    categoryItemPanel.setBackground(Colors.BUTTON_GOLD_BACKGROUND);
                    categoryLabelText.setForeground(Color.WHITE);

                    categoryItemPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Colors.BUTTON_GOLD_BACKGROUND.darker(), 3),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    categoryItemPanel.setBackground(Colors.BACKGROUND_SECONDARY);
                    categoryLabelText.setForeground(originalTextForeground);
                    categoryItemPanel.setBorder(defaultBorder);
                }
            });
            categoriesPanel.add(categoryItemPanel);
            categoriesPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainContentPanel, BorderLayout.CENTER);
    }
}
