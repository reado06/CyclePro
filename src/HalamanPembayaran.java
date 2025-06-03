import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HalamanPembayaran extends JFrame {
    private Produk selectedProduct;
    private JComboBox<String> paymentMethodComboBox;
    private JComboBox<String> courierComboBox;
    private JLabel vaInfoLabel; // Untuk menampilkan info VA

    public HalamanPembayaran(Produk product) {
        this.selectedProduct = product;
        setTitle("Pembayaran - CyclePro");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Kiri untuk Gambar Produk
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(260, 0));
        imagePanel.setBorder(BorderFactory.createTitledBorder("Gambar Produk"));

        // Panel Info Produk
        JPanel productInfoPanel = new JPanel();
        productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.Y_AXIS));
        productInfoPanel.setBorder(BorderFactory.createTitledBorder("Detail Produk"));
        productInfoPanel.add(new JLabel("Nama: " + selectedProduct.getName()));
        productInfoPanel.add(new JLabel("Kategori: " + selectedProduct.getCategory()));
        productInfoPanel.add(new JLabel("Harga: Rp " + String.format("%,.0f", selectedProduct.getPrice())));
        add(productInfoPanel, BorderLayout.NORTH);

        // Panel Opsi Pembayaran
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Metode Pembayaran
        gbc.gridx = 0; gbc.gridy = 0; optionsPanel.add(new JLabel("Metode Pembayaran:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        String[] paymentMethods = {"Virtual Account", "COD (Bayar di Tempat)"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        optionsPanel.add(paymentMethodComboBox, gbc);

        // Info VA (awalnya disembunyikan atau kosong)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        vaInfoLabel = new JLabel(" "); // Kosongkan dulu, atau beri placeholder
        vaInfoLabel.setForeground(Color.BLUE);
        optionsPanel.add(vaInfoLabel, gbc);
        gbc.gridwidth = 1; // reset

        // Kurir Ekspedisi
        gbc.gridx = 0; gbc.gridy = 2; optionsPanel.add(new JLabel("Kurir Ekspedisi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        String[] couriers = {"SiCepat", "JNE", "J&T Express"};
        courierComboBox = new JComboBox<>(couriers);
        optionsPanel.add(courierComboBox, gbc);

        // Alamat Pengiriman (ambil dari user yang login)
        gbc.gridx = 0; gbc.gridy = 3; optionsPanel.add(new JLabel("Alamat Pengiriman:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextArea addressArea = new JTextArea(Main.currentUser != null ? Main.currentUser.getAddress() : "Alamat tidak tersedia");
        addressArea.setRows(3);
        addressArea.setEditable(false); // Bisa dibuat editable jika diperlukan
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        optionsPanel.add(addressScrollPane, gbc);


        add(optionsPanel, BorderLayout.CENTER);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmPaymentButton = new JButton("Konfirmasi Pembayaran");
        JButton cancelButton = new JButton("Batal");
        buttonPanel.add(confirmPaymentButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listener untuk ComboBox Metode Pembayaran
        paymentMethodComboBox.addActionListener(e -> {
            String selectedMethod = (String) paymentMethodComboBox.getSelectedItem();
            if ("Virtual Account".equals(selectedMethod) && Main.currentUser != null) {
                vaInfoLabel.setText("Kode VA: 727" + Main.currentUser.getPhoneNumber());
            } else {
                vaInfoLabel.setText(" "); // Kosongkan jika bukan VA
            }
        });
        // Panggil sekali untuk inisialisasi
        paymentMethodComboBox.getActionListeners()[0].actionPerformed(null);


        confirmPaymentButton.addActionListener(e -> {
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            String courier = (String) courierComboBox.getSelectedItem();
            String shippingAddress = Main.currentUser != null ? Main.currentUser.getAddress() : "Tidak Diketahui";
            int userId = Main.currentUser != null ? Main.currentUser.getUserId() : -1;
            String vaNumber = null;

            if (userId == -1) {
                JOptionPane.showMessageDialog(this, "Error: User tidak teridentifikasi. Silakan login ulang.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Virtual Account".equals(paymentMethod)) {
                if (Main.currentUser == null || Main.currentUser.getPhoneNumber() == null || Main.currentUser.getPhoneNumber().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nomor telepon pengguna tidak ditemukan untuk Virtual Account.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                vaNumber = "727" + Main.currentUser.getPhoneNumber();
                DatabaseHelper.createOrder(userId, selectedProduct.getProductId(), shippingAddress, courier, paymentMethod, selectedProduct.getPrice(), vaNumber);
                JOptionPane.showMessageDialog(this, "Pesanan dengan Virtual Account " + vaNumber + " berhasil dibuat.\nSilakan selesaikan pembayaran.", "Info Pembayaran", JOptionPane.INFORMATION_MESSAGE);
            } else { // COD
                DatabaseHelper.createOrder(userId, selectedProduct.getProductId(), shippingAddress, courier, paymentMethod, selectedProduct.getPrice(), null);
                JOptionPane.showMessageDialog(this, "Pesanan Anda dengan metode COD akan segera diproses.", "Info Pemesanan", JOptionPane.INFORMATION_MESSAGE);
            }
            new Dashboard().setVisible(true);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            // Kembali ke halaman kategori sepeda sebelumnya
            new KategoriSepeda(selectedProduct.getCategory()).setVisible(true);
            dispose();
        });
    }
}
