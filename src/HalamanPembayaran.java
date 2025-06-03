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
    private JTextArea addressArea;

    public HalamanPembayaran(Produk product) {
        this.selectedProduct = product;
        setTitle("Pembayaran - CyclePro");
        setSize(500, 450); // Disesuaikan untuk setBounds
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Info Produk (Bagian Atas)
        JPanel productInfoPanel = new JPanel();
        productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.Y_AXIS));
        productInfoPanel.setBorder(BorderFactory.createTitledBorder("Detail Produk"));
        productInfoPanel.add(new JLabel("Nama: " + selectedProduct.getName()));
        productInfoPanel.add(new JLabel("Kategori: " + selectedProduct.getCategory()));
        productInfoPanel.add(new JLabel("Harga: Rp " + String.format("%,.0f", selectedProduct.getPrice())));
        add(productInfoPanel, BorderLayout.NORTH);

        // Panel Opsi Pembayaran (Tengah)
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(null); // Menggunakan null layout
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int yPos = 10;
        int labelWidth = 150;
        int fieldWidth = 250;
        int componentHeight = 25;
        int padding = 5;

        JLabel paymentMethodLabel = new JLabel("Metode Pembayaran:");
        paymentMethodLabel.setBounds(10, yPos, labelWidth, componentHeight);
        optionsPanel.add(paymentMethodLabel);

        String[] paymentMethods = {"Virtual Account", "COD (Bayar di Tempat)"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        paymentMethodComboBox.setBounds(10 + labelWidth + padding, yPos, fieldWidth, componentHeight);
        optionsPanel.add(paymentMethodComboBox);
        yPos += componentHeight + padding;

        vaInfoLabel = new JLabel(" ");
        vaInfoLabel.setForeground(Color.BLUE);
        vaInfoLabel.setBounds(10 + labelWidth + padding, yPos, fieldWidth + 50, componentHeight); // Lebarkan untuk teks VA
        optionsPanel.add(vaInfoLabel);
        yPos += componentHeight + padding;

        JLabel courierLabel = new JLabel("Kurir Ekspedisi:");
        courierLabel.setBounds(10, yPos, labelWidth, componentHeight);
        optionsPanel.add(courierLabel);

        String[] couriers = {"SiCepat", "JNE", "J&T Express"};
        courierComboBox = new JComboBox<>(couriers);
        courierComboBox.setBounds(10 + labelWidth + padding, yPos, fieldWidth, componentHeight);
        optionsPanel.add(courierComboBox);
        yPos += componentHeight + padding;

        JLabel addressLabel = new JLabel("Alamat Pengiriman:");
        addressLabel.setBounds(10, yPos, labelWidth, componentHeight);
        optionsPanel.add(addressLabel);

        addressArea = new JTextArea(Main.currentUser != null ? Main.currentUser.getAddress() : "Alamat tidak tersedia");
        addressArea.setRows(3);
        addressArea.setEditable(false);
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        addressScrollPane.setBounds(10 + labelWidth + padding, yPos, fieldWidth, 60); // Tinggi disesuaikan untuk TextArea
        optionsPanel.add(addressScrollPane);

        add(optionsPanel, BorderLayout.CENTER);

        // Panel Tombol (Bawah)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmPaymentButton = new JButton("Konfirmasi Pembayaran");
        JButton cancelButton = new JButton("Batal");
        buttonPanel.add(confirmPaymentButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        paymentMethodComboBox.addActionListener(e -> {
            String selectedMethod = (String) paymentMethodComboBox.getSelectedItem();
            if ("Virtual Account".equals(selectedMethod) && Main.currentUser != null) {
                vaInfoLabel.setText("Kode VA: 727" + Main.currentUser.getPhoneNumber());
            } else {
                vaInfoLabel.setText(" ");
            }
        });
        
        if (paymentMethodComboBox.getActionListeners().length > 0) {
            paymentMethodComboBox.getActionListeners()[0].actionPerformed(null);
        }


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
            new KategoriSepeda(selectedProduct.getCategory()).setVisible(true);
            dispose();
        });
    }
}