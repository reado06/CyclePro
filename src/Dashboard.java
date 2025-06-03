import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Dashboard - CyclePro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // * Panel Atas (Profil)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String username = (Main.currentUser != null) ? Main.currentUser.getUsername() : "Guest";
        JLabel profileLabel = new JLabel("Profil: " + username + " ");
        topPanel.add(profileLabel);
        add(topPanel, BorderLayout.NORTH);

        // Panel Tengah (Kategori Sepeda)
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS)); // List ke bawah
        categoriesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] bikeTypes = {"Sepeda BMX", "Sepeda Gunung", "Sepeda Lipat"};
        String[] categoriesDB = {"BMX", "Gunung", "Lipat"};
        String[] imagePaths = {
            "img\\imgDashboard\\sepedaBMX.png",
            "img\\imgDashboard\\sepedaGunung.png",
            "img\\imgDashboard\\sepedaLIPAT.png"
        };

        for (int i = 0; i < bikeTypes.length; i++) {
            final String categoryName = bikeTypes[i];
            final String categoryDBName = categoriesDB[i];
            final String imagePath = imagePaths[i]; // Untuk Path Gambar yang sesuai

            JPanel categoryItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
            categoryItemPanel.setBorder(BorderFactory.createEtchedBorder());
            categoryItemPanel.setPreferredSize(new Dimension(700, 120)); // Atur ukuran panel item

            JLabel imageLabel = new JLabel();
            try {
                java.net.URL imgUrl = getClass().getResource(imagePath.replace("\\", "/"));
                if (imgUrl != null) {
                    ImageIcon bikeIcon = new ImageIcon(imgUrl);
                    Image image = bikeIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                }
            } catch (Exception e) {
                imageLabel.setText("Gagal Muat"); // Jika gambar gagal dimuat
                imageLabel.setOpaque(true);
                imageLabel.setBackground(Color.LIGHT_GRAY);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                System.err.println("Error memuat gambar: " + imagePath + " - " + e.getMessage());
                e.printStackTrace();
            }
            imageLabel.setPreferredSize(new Dimension(100, 100)); // Ukuran label gambar
            categoryItemPanel.add(imageLabel); // tambah label gambar ke panel item

            JLabel categoryLabel = new JLabel(categoryName);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));

            categoryItemPanel.add(categoryLabel);

            categoryItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            categoryItemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new KategoriSepeda(categoryDBName).setVisible(true);
                    dispose();
                }
            });
            categoriesPanel.add(categoryItemPanel);
            categoriesPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spasi antar kategori
        }

        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Tombol Logout (opsional)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            Main.currentUser = null;
            new HalamanLogin().setVisible(true);
            dispose();
        });
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
