import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;  

public class HalamanRiwayatPesanan extends JFrame {

    private DefaultTableModel tableModel;
    private JTable historyTable;

    public HalamanRiwayatPesanan() {
        setTitle("Riwayat Pesanan Anda");
        setSize(900, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
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
                new Dashboard().setVisible(true); 
                dispose(); 
            }
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- Panel Tengah (Tabel Riwayat Pesanan) ---
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainContentPanel.setBackground(Colors.BACKGROUND_PRIMARY);

        tableModel = new DefaultTableModel(new Object[]{"ID Pesanan", "Produk", "Tanggal", "Total Harga", "Status", "Kurir", "Alamat Pengiriman"}, 0) { 
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
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

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        tableModel.setRowCount(0); 
        if (Main.currentUser != null) {
            List<Pesanan> userOrders = DatabaseHelper.getOrdersByUserId(Main.currentUser.getUserId());

            if (userOrders.isEmpty()) {
                System.out.println("Tidak ada riwayat pesanan ditemukan untuk user ini.");

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
                        order.getShippingAddress()
                    });
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Anda harus login untuk melihat riwayat pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Tidak ada user yang sedang login untuk melihat riwayat pesanan.");
        }
    }

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

    public static void main(String[] args) {
        DatabaseHelper.createNewDatabase();
        DatabaseHelper.createTables();
        DatabaseHelper.addDefaultAdmin();

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