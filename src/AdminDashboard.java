import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 

public class AdminDashboard extends JFrame {

    private DefaultTableModel tableModel;
    private JTable ordersTable;

    public AdminDashboard() {
        setTitle("Admin Dashboard - Kelola Pesanan");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);

        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(Colors.NAVBAR_BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Data Pesanan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Colors.NAVBAR_TEXT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JLabel adminInfoLabel = new JLabel("Admin: " + (Main.currentUser != null ? Main.currentUser.getUsername() : "N/A"));
        adminInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        adminInfoLabel.setForeground(Colors.NAVBAR_TEXT);
        headerPanel.add(adminInfoLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainContentPanel.setBackground(Colors.BACKGROUND_PRIMARY);

        tableModel = new DefaultTableModel(new Object[]{"ID Pesanan", "User", "Produk", "Tanggal", "Total", "Status", "Alamat Pengiriman", "Kurir", "Metode Bayar", "VA Number"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(tableModel);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 12));
        ordersTable.setRowHeight(25);
        ordersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        ordersTable.getTableHeader().setBackground(Colors.BUTTON_PRIMARY_BACKGROUND);
        ordersTable.getTableHeader().setForeground(Colors.BUTTON_PRIMARY_TEXT);
        ordersTable.setSelectionBackground(Colors.BUTTON_PRIMARY_BACKGROUND.brighter());
        ordersTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR, 1));
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        actionPanel.setBackground(Colors.BACKGROUND_PRIMARY);

        JButton refreshButton = new JButton("Refresh Data");
        styleButton(refreshButton, Colors.BUTTON_PRIMARY_BACKGROUND, Colors.BUTTON_PRIMARY_TEXT);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadOrdersData();
            }
        });
        actionPanel.add(refreshButton);

        JButton editStatusButton = new JButton("Ubah Status Pesanan");
        styleButton(editStatusButton, Colors.BUTTON_SUCCESS_BACKGROUND, Colors.BUTTON_SUCCESS_TEXT);
        editStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ordersTable.getSelectedRow();
                if (selectedRow != -1) {
                    int orderId = (int) ordersTable.getValueAt(selectedRow, 0);
                    String currentStatus = (String) ordersTable.getValueAt(selectedRow, 5);

                    String[] statusOptions = {"Pending", "Diproses", "Dikirim", "Selesai", "Dibatalkan"};
                    String newStatus = (String) JOptionPane.showInputDialog(
                        AdminDashboard.this,
                        "Pilih status baru untuk Pesanan ID: " + orderId + " (Status saat ini: " + currentStatus + ")",
                        "Ubah Status Pesanan",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        statusOptions,
                        currentStatus
                    );

                    if (newStatus != null && !newStatus.equals(currentStatus)) {
                        if (DatabaseHelper.updateOrderStatus(orderId, newStatus)) {
                            JOptionPane.showMessageDialog(AdminDashboard.this, "Status pesanan berhasil diperbarui!");
                            loadOrdersData();
                        } else {
                            JOptionPane.showMessageDialog(AdminDashboard.this, "Gagal memperbarui status pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Pilih pesanan yang akan diubah statusnya terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        actionPanel.add(editStatusButton);

        JButton deleteOrderButton = new JButton("Hapus Pesanan");
        styleButton(deleteOrderButton, Colors.BUTTON_DANGER_BACKGROUND, Colors.BUTTON_DANGER_TEXT);
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ordersTable.getSelectedRow();
                if (selectedRow != -1) {
                    int orderId = (int) ordersTable.getValueAt(selectedRow, 0);
                    String userName = (String) ordersTable.getValueAt(selectedRow, 1);
                    String productName = (String) ordersTable.getValueAt(selectedRow, 2);

                    int confirm = JOptionPane.showConfirmDialog(AdminDashboard.this,
                            "Yakin ingin menghapus pesanan ID: " + orderId + " dari " + userName + " untuk produk " + productName + "?",
                            "Konfirmasi Hapus Pesanan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if (DatabaseHelper.deleteOrder(orderId)) {
                            JOptionPane.showMessageDialog(AdminDashboard.this, "Pesanan berhasil dihapus!");
                            loadOrdersData();
                        } else {
                            JOptionPane.showMessageDialog(AdminDashboard.this, "Gagal menghapus pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Pilih pesanan yang akan dihapus terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        actionPanel.add(deleteOrderButton);
        
        JButton logoutButton = new JButton("Logout Admin");
        styleButton(logoutButton, Colors.BUTTON_SECONDARY_BACKGROUND, Colors.BUTTON_SECONDARY_TEXT);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.currentUser = null;
                new HalamanLogin().setVisible(true);
                dispose();
            }
        });
        actionPanel.add(logoutButton);

        add(actionPanel, BorderLayout.SOUTH);

        loadOrdersData();
    }

    private void loadOrdersData() {
        tableModel.setRowCount(0);
        List<Pesanan> orders = DatabaseHelper.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("Tidak ada pesanan ditemukan.");
        } else {
            for (Pesanan order : orders) {
                String userName = (order.getUser() != null) ? order.getUser().getUsername() : "N/A";
                String productName = (order.getProduct() != null) ? order.getProduct().getName() : "N/A";

                tableModel.addRow(new Object[]{
                    order.getOrderId(),
                    userName,
                    productName,
                    order.getOrderDate(),
                    String.format("Rp %,.0f", order.getTotalPrice()),
                    order.getStatus(),
                    order.getShippingAddress(),
                    order.getCourier(),
                    order.getPaymentMethod(),
                    order.getVaNumber() != null ? order.getVaNumber() : "-"
                });
            }
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

        Main.currentUser = DatabaseHelper.authenticateAdmin("admin", "admin123");

        SwingUtilities.invokeLater(() -> {
            if (Main.currentUser instanceof Admin) {
                new AdminDashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Anda harus login sebagai Admin untuk mengakses dashboard ini.", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
                new HalamanLogin().setVisible(true);
            }
        });
    }
}
