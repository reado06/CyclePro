// HalamanRiwayatPesanan.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter; // Pastikan import ini ada
import java.awt.event.MouseEvent;   // Pastikan import ini ada

public class HalamanRiwayatPesanan extends JFrame {

    private DefaultTableModel tableModel;
    private JTable historyTable;

    public HalamanRiwayatPesanan() {
        setTitle("Riwayat Pesanan Anda");
        setSize(900, 600); // Ukuran disesuaikan
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tutup hanya jendela ini, tidak seluruh aplikasi
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        // --- Panel Atas (Header) ---
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(Colors.NAVBAR_BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Riwayat Pesanan Anda");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Colors.NAVBAR_TEXT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Kembali ke Dashboard");
        styleButton(backButton, Colors.BUTTON_SECONDARY_BACKGROUND, Colors.BUTTON_SECONDARY_TEXT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard().setVisible(true); // Kembali ke Dashboard user
                dispose(); // Tutup halaman riwayat ini
            }
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- Panel Tengah (Tabel Riwayat Pesanan) ---
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainContentPanel.setBackground(Colors.BACKGROUND_PRIMARY);

        // Kolom untuk tabel riwayat pesanan (sesuaikan jika perlu)
        tableModel = new DefaultTableModel(new Object[]{"ID Pesanan", "Produk", "Tanggal", "Total Harga", "Status", "Kurir", "Alamat Pengiriman"}, 0) { // Tambahkan kolom alamat
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // User tidak bisa mengedit langsung
            }
        };
        historyTable = new JTable(tableModel);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 12));
        historyTable.setRowHeight(25);
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        historyTable.getTableHeader().setBackground(Colors.BUTTON_PRIMARY_BACKGROUND);
        historyTable.getTableHeader().setForeground(Colors.BUTTON_PRIMARY_TEXT);
        historyTable.setSelectionBackground(Colors.BUTTON_PRIMARY_BACKGROUND.brighter());
        historyTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR, 1));
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);

        // Muat data riwayat pesanan saat halaman dibuka
        loadOrderHistory();
    }

    // Metode untuk memuat riwayat pesanan user yang sedang login
    private void loadOrderHistory() {
        tableModel.setRowCount(0); // Hapus baris yang ada
        if (Main.currentUser != null) {
            List<Pesanan> userOrders = DatabaseHelper.getOrdersByUserId(Main.currentUser.getUserId());

            if (userOrders.isEmpty()) {
                System.out.println("Tidak ada riwayat pesanan ditemukan untuk user ini.");
                // Opsional: tampilkan pesan di UI jika tabel kosong
                // JLabel noOrderLabel = new JLabel("Anda belum memiliki riwayat pesanan.");
                // noOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                // mainContentPanel.add(noOrderLabel, BorderLayout.NORTH); // Contoh
            } else {
                for (Pesanan order : userOrders) {
                    String productName = (order.getProduct() != null) ? order.getProduct().getName() : "N/A";
                    tableModel.addRow(new Object[]{
                        order.getOrderId(),
                        productName,
                        order.getOrderDate(),
                        String.format("Rp %,.0f", order.getTotalPrice()),
                        order.getStatus(),
                        order.getCourier(),
                        order.getShippingAddress() // Tambahkan alamat pengiriman
                    });
                }
            }
        } else {
            // Ini akan muncul jika somehow user tidak login tapi halaman ini dibuka
            JOptionPane.showMessageDialog(this, "Anda harus login untuk melihat riwayat pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Tidak ada user yang sedang login untuk melihat riwayat pesanan.");
        }
    }

    // Metode pembantu untuk menata gaya tombol (copy dari AdminDashboard)
    private void styleButton(JButton button, Color bgColor, Color textColor) {
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    // Main method untuk pengujian mandiri (opsional, bisa dihapus/dibiarkan)
    public static void main(String[] args) {
        // Untuk testing, pastikan database dan user sudah ada
        DatabaseHelper.createNewDatabase();
        DatabaseHelper.createTables();
        DatabaseHelper.addDefaultAdmin(); // Jika perlu admin juga

        // Contoh: login sebagai user dummy untuk melihat riwayat
        // Main.currentUser = DatabaseHelper.authenticateUser("user1", "pass1");
        // if (Main.currentUser == null) {
        //     DatabaseHelper.registerUser("user1", "pass1", "Alamat user1", "08123");
        //     Main.currentUser = DatabaseHelper.authenticateUser("user1", "pass1");
        // }

        SwingUtilities.invokeLater(() -> {
            if (Main.currentUser != null) {
                new HalamanRiwayatPesanan().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Login sebagai user untuk melihat riwayat pesanan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                new HalamanLogin().setVisible(true);
            }
        });
    }
}