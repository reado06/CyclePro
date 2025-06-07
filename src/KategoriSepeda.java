import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;

public class KategoriSepeda extends JFrame {
    private String category;

    public KategoriSepeda(String category) {
        this.category = category;
        setTitle("Daftar Sepeda " + category + " - CyclePro");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,0));
        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        headerPanel.setBackground(Colors.NAVBAR_BACKGROUND);

        JButton backButton = new JButton("Kembali ke Dashboard");
        backButton.setBackground(Colors.BUTTON_SECONDARY_BACKGROUND);
        backButton.setForeground(Colors.BUTTON_SECONDARY_TEXT);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        headerPanel.add(backButton);

        headerPanel.add(Box.createHorizontalStrut(20));

        JLabel titleLabelText = new JLabel("Pilih Sepeda " + category);
        titleLabelText.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelText.setForeground(Colors.NAVBAR_TEXT);
        headerPanel.add(titleLabelText);
        add(headerPanel, BorderLayout.NORTH);

        JPanel bikesPanelWrapper = new JPanel(new BorderLayout());
        bikesPanelWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        bikesPanelWrapper.setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel bikesPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        bikesPanel.setBackground(Colors.BACKGROUND_PRIMARY);
        List<Produk> productList = DatabaseHelper.getProductsByCategory(category);

        if (productList.isEmpty()) {
            bikesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel noProductLabel = new JLabel("Tidak ada produk untuk kategori ini.");
            noProductLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noProductLabel.setForeground(Colors.TEXT_SECONDARY);
            bikesPanel.add(noProductLabel);
        } else {
            for (Produk product : productList) {
                JPanel bikeItemPanel = new JPanel();
                bikeItemPanel.setLayout(new BoxLayout(bikeItemPanel, BoxLayout.Y_AXIS));
                bikeItemPanel.setBackground(Colors.BACKGROUND_SECONDARY);
                
                // Simpan border default untuk dikembalikan saat mouseExited
                Border defaultBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colors.BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(10,10,10,10)
                );
                bikeItemPanel.setBorder(defaultBorder);
                bikeItemPanel.setPreferredSize(new Dimension(250, 320));

                JLabel imageDisplayLabel = new JLabel();
                imageDisplayLabel.setPreferredSize(new Dimension(200, 150));
                imageDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageDisplayLabel.setVerticalAlignment(SwingConstants.CENTER);
                imageDisplayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                String subfolder;
                if ("BMX".equalsIgnoreCase(category)) {
                    subfolder = "imgBMX";
                } else if ("Gunung".equalsIgnoreCase(category)) {
                    subfolder = "imgGunung";
                } else if ("Lipat".equalsIgnoreCase(category)) {
                    subfolder = "imgLipat";
                } else {
                    subfolder = "produk";
                }

                String resourcePath = "/img/" + subfolder + "/" + product.getImagePath();

                try {
                    java.net.URL imgUrl = getClass().getResource(resourcePath);
                    if (imgUrl != null) {
                        ImageIcon originalIcon = new ImageIcon(imgUrl);
                        Image originalImage = originalIcon.getImage();
                        Image scaledImage = originalImage.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                        imageDisplayLabel.setIcon(new ImageIcon(scaledImage));
                    } else {
                        System.err.println("Resource gambar tidak ditemukan: " + resourcePath);
                        imageDisplayLabel.setText("<html><div style='width:180px;height:130px;text-align:center;color:black;'>Gmbr Tdk<br>Ditemukan:<br>" + product.getImagePath() + "</div></html>");
                    }
                } catch (Exception ex) {
                    System.err.println("Error memuat resource gambar: " + resourcePath + " - " + ex.getMessage());
                    imageDisplayLabel.setText("Gagal Muat");
                    imageDisplayLabel.setForeground(Color.RED);
                }
                
                imageDisplayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel nameLabel = new JLabel(product.getName());
                nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                nameLabel.setForeground(Colors.TEXT_PRIMARY);
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel priceLabel = new JLabel(String.format("Rp %,.0f", product.getPrice()));
                priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                priceLabel.setForeground(Colors.TEXT_PRIMARY);
                priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel stockLabel = new JLabel("Stok: " + product.getStock());
                stockLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                stockLabel.setForeground(product.getStock() > 0 ? Colors.TEXT_SECONDARY : Colors.BUTTON_DANGER_BACKGROUND);
                stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                bikeItemPanel.add(Box.createVerticalStrut(10));
                bikeItemPanel.add(imageDisplayLabel);
                bikeItemPanel.add(Box.createVerticalStrut(10));
                bikeItemPanel.add(nameLabel);
                bikeItemPanel.add(priceLabel);
                bikeItemPanel.add(stockLabel);
                bikeItemPanel.add(Box.createVerticalStrut(10));


                bikeItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                bikeItemPanel.addMouseListener(new MouseAdapter() {
                    // Simpan referensi warna asli untuk dikembalikan saat mouseExited
                    Color originalBackground = bikeItemPanel.getBackground();
                    Color originalNameForeground = nameLabel.getForeground();
                    Color originalPriceForeground = priceLabel.getForeground();
                    Color originalStockForeground = stockLabel.getForeground();


                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (product.getStock() > 0) {
                            new HalamanPembayaran(product).setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(KategoriSepeda.this, "Stok produk ini habis.", "Stok Habis", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // Ubah background menjadi warna emas (sama seperti Dashboard)
                        bikeItemPanel.setBackground(Colors.BUTTON_GOLD_BACKGROUND);
                        // Ubah warna teks menjadi putih agar kontras
                        nameLabel.setForeground(Color.WHITE);
                        priceLabel.setForeground(Color.WHITE);
                        stockLabel.setForeground(Color.WHITE);

                        // Efek pop-up: border menjadi lebih tebal dan warna berbeda (sama seperti Dashboard)
                        bikeItemPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Colors.BUTTON_GOLD_BACKGROUND.darker(), 3), // Border lebih tebal, warna lebih gelap dari emas
                            BorderFactory.createEmptyBorder(10,10,10,10)
                        ));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Kembalikan ke warna dan border semula
                        bikeItemPanel.setBackground(originalBackground);
                        nameLabel.setForeground(originalNameForeground);
                        priceLabel.setForeground(originalPriceForeground);
                        stockLabel.setForeground(originalStockForeground);
                        bikeItemPanel.setBorder(defaultBorder); // Kembali ke border default yang sudah disimpan
                    }
                });
                bikesPanel.add(bikeItemPanel);
            }
        }
        bikesPanelWrapper.add(bikesPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(bikesPanelWrapper);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            new Dashboard().setVisible(true);
            dispose();
        });
    }
}