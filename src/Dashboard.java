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
        setLayout(new BorderLayout(0, 0)); 
        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); 
        topPanel.setBackground(Colors.NAVBAR_BACKGROUND);
        String username = (Main.currentUser != null) ? Main.currentUser.getUsername() : "Guest"; 
        JLabel profileLabel = new JLabel("Profil: " + username + " ");
        profileLabel.setForeground(Colors.NAVBAR_TEXT);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(profileLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel categoriesOuterPanel = new JPanel(new BorderLayout());
        categoriesOuterPanel.setBackground(Colors.BACKGROUND_PRIMARY);
        categoriesOuterPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        categoriesPanel.setBackground(Colors.BACKGROUND_PRIMARY);


        String[] bikeTypes = {"Sepeda BMX", "Sepeda Gunung", "Sepeda Lipat"}; 
        String[] categoriesDB = {"BMX", "Gunung", "Lipat"}; 
        String[] imagePaths = { 
            "img/imgDashboard/sepedaBMX.png",
            "img/imgDashboard/sepedaGunung.png",
            "img/imgDashboard/sepedaLIPAT.png"
        };

        for (int i = 0; i < bikeTypes.length; i++) {
            final String categoryName = bikeTypes[i];
            final String categoryDBName = categoriesDB[i];
            final String imagePath = imagePaths[i];

            JPanel categoryItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
            categoryItemPanel.setBackground(Colors.BACKGROUND_SECONDARY);
            categoryItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER_COLOR, 1), 
                BorderFactory.createEmptyBorder(10,10,10,10) 
            ));
            categoryItemPanel.setPreferredSize(new Dimension(700, 120));
            categoryItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));


            JLabel imageLabel = new JLabel();
            try {
                java.net.URL imgUrl = getClass().getResource(imagePath);
                if (imgUrl != null) {
                    ImageIcon bikeIcon = new ImageIcon(imgUrl);
                    Image image = bikeIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                } else {
                    imageLabel.setText("Gambar tidak ditemukan: " + imagePath);
                    imageLabel.setOpaque(true);
                    imageLabel.setBackground(Color.LIGHT_GRAY);
                    imageLabel.setForeground(Color.BLACK);
                }
            } catch (Exception e) {
                imageLabel.setText("Gagal Muat Gambar");
                imageLabel.setOpaque(true);
                imageLabel.setBackground(Color.LIGHT_GRAY);
                imageLabel.setForeground(Color.BLACK);
            }
            imageLabel.setPreferredSize(new Dimension(100, 100));
            categoryItemPanel.add(imageLabel);

            JLabel categoryLabelText = new JLabel(categoryName);
            categoryLabelText.setFont(new Font("Arial", Font.BOLD, 18));
            categoryLabelText.setForeground(Colors.TEXT_PRIMARY);
            categoryItemPanel.add(categoryLabelText);

            categoryItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            categoryItemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new KategoriSepeda(categoryDBName).setVisible(true);
                    dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    categoryItemPanel.setBackground(Colors.BACKGROUND_PRIMARY); 
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    categoryItemPanel.setBackground(Colors.BACKGROUND_SECONDARY); 
                }
            });
            categoriesPanel.add(categoryItemPanel);
            categoriesPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        scrollPane.setBackground(Colors.BACKGROUND_PRIMARY);
        categoriesOuterPanel.add(scrollPane, BorderLayout.CENTER); 
        add(categoriesOuterPanel, BorderLayout.CENTER); 


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15,10));
        bottomPanel.setBackground(Colors.NAVBAR_BACKGROUND); 
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Colors.BUTTON_DANGER_BACKGROUND);
        logoutButton.setForeground(Colors.BUTTON_DANGER_TEXT);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.addActionListener(e -> {
            Main.currentUser = null; 
            new HalamanLogin().setVisible(true);
            dispose();
        });
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
