import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class KategoriSepeda extends JFrame {
    private String category;

    public KategoriSepeda(String category) {
        this.category = category;
        setTitle("Daftar Sepeda " + category + " - CyclePro");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Pilih Sepeda " + category);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JButton backButton = new JButton("Kembali ke Dashboard");
        headerPanel.add(backButton);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);


        JPanel bikesPanel = new JPanel(new GridLayout(0, 3, 15, 15)); // 3 kolom, jarak 15px
        bikesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        List<Produk> productList = DatabaseHelper.getProductsByCategory(category);

        if (productList.isEmpty()) {
            bikesPanel.setLayout(new FlowLayout()); // Ganti layout jika kosong
            bikesPanel.add(new JLabel("Tidak ada produk untuk kategori ini."));
        } else {
            // Batasi hingga 6 placeholder atau sesuai jumlah produk
            int displayCount = Math.min(productList.size(), 6);
            for (int i = 0; i < displayCount; i++) {
                Produk product = productList.get(i);
                JPanel bikeItemPanel = new JPanel();
                bikeItemPanel.setLayout(new BoxLayout(bikeItemPanel, BoxLayout.Y_AXIS));
                bikeItemPanel.setBorder(BorderFactory.createEtchedBorder());
                bikeItemPanel.setPreferredSize(new Dimension(250, 300)); // Ukuran panel produk

                JLabel imgPlaceholder = new JLabel("<html><div style='width:200px;height:150px;background-color:lightgray;text-align:center;line-height:150px;color:black;'>Gbr: " + product.getName() +"</div></html>", SwingConstants.CENTER);
                imgPlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel nameLabel = new JLabel(product.getName());
                nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel priceLabel = new JLabel(String.format("Rp %,.0f", product.getPrice()));
                priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel stockLabel = new JLabel("Stok: " + product.getStock());
                stockLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                bikeItemPanel.add(Box.createVerticalStrut(10));
                bikeItemPanel.add(imgPlaceholder);
                bikeItemPanel.add(Box.createVerticalStrut(10));
                bikeItemPanel.add(nameLabel);
                bikeItemPanel.add(priceLabel);
                bikeItemPanel.add(stockLabel);
                bikeItemPanel.add(Box.createVerticalStrut(10));


                bikeItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                bikeItemPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (product.getStock() > 0) {
                            new HalamanPembayaran(product).setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(KategoriSepeda.this, "Stok produk ini habis.", "Stok Habis", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                bikesPanel.add(bikeItemPanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(bikesPanel);
        add(scrollPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            new Dashboard().setVisible(true);
            dispose();
        });
    }
}
