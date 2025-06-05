import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HalamanPembayaran extends JFrame {
    private Produk selectedProduct;
    private JComboBox<String> paymentMethodComboBox;
    private JComboBox<String> courierComboBox;
    private JLabel vaInfoLabel;
    private JTextArea addressArea;
    private JTextArea productDescriptionArea;

    public HalamanPembayaran(Produk product) {
        this.selectedProduct = product;
        setTitle("Pembayaran - CyclePro");
        setSize(950, 700); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Colors.BACKGROUND_PRIMARY); 

        JPanel topBannerPanel = new JPanel(new BorderLayout(10, 0));
        topBannerPanel.setBackground(Colors.NAVBAR_BACKGROUND); 
        topBannerPanel.setBorder(new EmptyBorder(5, 15, 5, 15));

        JLabel logoLabel = new JLabel("CYCLEPRO"); 
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
        logoLabel.setForeground(Colors.NAVBAR_TEXT); 
        topBannerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel taglineLabel = new JLabel("Solusi Kebutuhan Sepeda Anda"); 
        taglineLabel.setFont(new Font("Arial", Font.ITALIC, 14)); 
        taglineLabel.setForeground(Colors.NAVBAR_TEXT); 
        taglineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topBannerPanel.add(taglineLabel, BorderLayout.CENTER);
        add(topBannerPanel, BorderLayout.NORTH);

        JPanel mainPagePanel = new JPanel(new BorderLayout(0,0));
        mainPagePanel.setBackground(Colors.BACKGROUND_PRIMARY); 
        add(mainPagePanel, BorderLayout.CENTER);


        JPanel productInfoBarPanel = new JPanel(new BorderLayout());
        productInfoBarPanel.setBackground(new Color(255, 204, 0));
        productInfoBarPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel productNameLabelBar = new JLabel(selectedProduct.getName()); 
        productNameLabelBar.setFont(new Font("Arial", Font.BOLD, 28)); 
        productNameLabelBar.setForeground(Color.BLACK);
        productInfoBarPanel.add(productNameLabelBar, BorderLayout.WEST);

        JLabel productPriceLabelBar = new JLabel(String.format("Rp %,.0f", selectedProduct.getPrice())); 
        productPriceLabelBar.setFont(new Font("Arial", Font.BOLD, 28)); 
        productPriceLabelBar.setForeground(Color.BLACK);
        productInfoBarPanel.add(productPriceLabelBar, BorderLayout.EAST);
        mainPagePanel.add(productInfoBarPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainContentPanel = new JPanel(new BorderLayout(15, 15));
        mainContentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainContentPanel.setBackground(Colors.BACKGROUND_PRIMARY); 
        mainPagePanel.add(mainContentPanel, BorderLayout.CENTER);

        // Panel Gambar Produk
        JPanel productImagePanel = new JPanel(new BorderLayout());
        productImagePanel.setBackground(Colors.BACKGROUND_SECONDARY); 
        productImagePanel.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR)); 
        productImagePanel.setPreferredSize(new Dimension(400, 0)); 

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        String subfolder;
        String category = selectedProduct.getCategory(); 
        if ("BMX".equalsIgnoreCase(category)) {
            subfolder = "imgBMX";
        } else if ("Gunung".equalsIgnoreCase(category)) {
            subfolder = "imgGunung";
        } else if ("Lipat".equalsIgnoreCase(category)) {
            subfolder = "imgLipat";
        } else {
            subfolder = "produk"; 
        }

        // Path untuk classpath resource
        String resourcePath = "/img/" + subfolder + "/" + selectedProduct.getImagePath(); 

        try {
            java.net.URL imgUrl = getClass().getResource(resourcePath);

            if (imgUrl != null) {
                ImageIcon bikeIcon = new ImageIcon(imgUrl);
                Image image = bikeIcon.getImage().getScaledInstance(380, 280, Image.SCALE_SMOOTH); 
                imageLabel.setIcon(new ImageIcon(image));
            } else {
                imageLabel.setText("<html><div style='width:380px;height:280px;background-color:#ECECEC;text-align:center;line-height:140px;color:black;border:1px solid #DADADA;'>Gambar:<br>" + selectedProduct.getName() +"<br>tidak ditemukan ("+ resourcePath +")</div></html>"); 
                System.err.println("Resource gambar tidak ditemukan: " + resourcePath);
            }
        } catch (Exception e) {
            imageLabel.setText("Gagal memuat gambar produk.");
            System.err.println("Error memuat resource gambar: " + resourcePath + " - " + e.getMessage());
            e.printStackTrace();
        }
        
        productImagePanel.add(imageLabel, BorderLayout.CENTER);
        mainContentPanel.add(productImagePanel, BorderLayout.EAST);


        // Panel Detail dan Form
        JPanel detailsFormPanel = new JPanel();
        detailsFormPanel.setLayout(null);
        detailsFormPanel.setBackground(Colors.BACKGROUND_SECONDARY); 
        detailsFormPanel.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR)); 
        mainContentPanel.add(detailsFormPanel, BorderLayout.CENTER);

        int yPos = 20;
        int xMargin = 20;
        int labelWidth = 150;
        int fieldWidth = 250;
        int componentHeight = 25;
        int areaHeight = 70;
        int spacing = 15;

        // Deskripsi Produk
        JLabel descLabel = new JLabel("Deskripsi Produk:"); 
        descLabel.setBounds(xMargin, yPos, labelWidth, componentHeight); 
        descLabel.setForeground(Colors.TEXT_PRIMARY); 
        descLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
        detailsFormPanel.add(descLabel); 
        yPos += componentHeight + 5;

        productDescriptionArea = new JTextArea(selectedProduct.getDescription());  
        productDescriptionArea.setEditable(false); 
        productDescriptionArea.setLineWrap(true); 
        productDescriptionArea.setWrapStyleWord(true); 
        productDescriptionArea.setFont(new Font("Arial", Font.PLAIN, 13)); 
        productDescriptionArea.setBackground(Colors.BACKGROUND_PRIMARY); 
        productDescriptionArea.setForeground(Colors.TEXT_SECONDARY); 
        JScrollPane descScrollPane = new JScrollPane(productDescriptionArea);
        descScrollPane.setBounds(xMargin, yPos, fieldWidth + 100, areaHeight); 
        descScrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR)); 
        detailsFormPanel.add(descScrollPane); 
        yPos += areaHeight + spacing;

        // Metode Pembayaran
        JLabel paymentMethodLabelText = new JLabel("Metode Pembayaran:"); 
        paymentMethodLabelText.setBounds(xMargin, yPos, labelWidth, componentHeight); 
        paymentMethodLabelText.setForeground(Colors.TEXT_PRIMARY); 
        detailsFormPanel.add(paymentMethodLabelText); 

        String[] paymentMethods = {"Virtual Account", "COD (Bayar di Tempat)"}; 
        paymentMethodComboBox = new JComboBox<>(paymentMethods); 
        paymentMethodComboBox.setBounds(xMargin + labelWidth + 10, yPos, fieldWidth, componentHeight); 
        paymentMethodComboBox.setBackground(Color.WHITE); 
        paymentMethodComboBox.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR)); 
        detailsFormPanel.add(paymentMethodComboBox); 
        yPos += componentHeight + 5;

        vaInfoLabel = new JLabel(" "); 
        vaInfoLabel.setForeground(Colors.TEXT_LINK); 
        vaInfoLabel.setFont(new Font("Arial", Font.BOLD, 12)); 
        vaInfoLabel.setBounds(xMargin + labelWidth + 10, yPos, fieldWidth + 50, componentHeight); 
        detailsFormPanel.add(vaInfoLabel); 
        yPos += componentHeight + spacing;

        // Kurir Ekspedisi
        JLabel courierLabelText = new JLabel("Kurir Ekspedisi:"); 
        courierLabelText.setBounds(xMargin, yPos, labelWidth, componentHeight); 
        courierLabelText.setForeground(Colors.TEXT_PRIMARY); 
        detailsFormPanel.add(courierLabelText); 

        String[] couriers = {"SiCepat", "JNE", "J&T Express"}; 
        courierComboBox = new JComboBox<>(couriers); 
        courierComboBox.setBounds(xMargin + labelWidth + 10, yPos, fieldWidth, componentHeight); 
        courierComboBox.setBackground(Color.WHITE); 
        courierComboBox.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR)); 
        detailsFormPanel.add(courierComboBox); 
        yPos += componentHeight + spacing;

        // Alamat Pengiriman
        JLabel addressLabelText = new JLabel("Alamat Pengiriman:"); 
        addressLabelText.setBounds(xMargin, yPos, labelWidth, componentHeight); 
        addressLabelText.setForeground(Colors.TEXT_PRIMARY); 
        detailsFormPanel.add(addressLabelText); 
        yPos += componentHeight + 5;
        
        addressArea = new JTextArea(Main.currentUser != null ? Main.currentUser.getAddress() : "Alamat tidak tersedia");  
        addressArea.setEditable(false);
        addressArea.setLineWrap(true); 
        addressArea.setWrapStyleWord(true); 
        addressArea.setBackground(Colors.BACKGROUND_PRIMARY); 
        addressArea.setForeground(Colors.TEXT_SECONDARY); 
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        addressScrollPane.setBounds(xMargin, yPos, fieldWidth + 100, areaHeight - 20);  
        addressScrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR)); 
        detailsFormPanel.add(addressScrollPane); 
        yPos += (areaHeight - 20) + spacing + 10;

        // Tombol Aksi (Confirm & Cancel)
        JButton confirmPaymentButton = new JButton("BELI SEKARANG"); 
        confirmPaymentButton.setBackground(Colors.BUTTON_SUCCESS_BACKGROUND); 
        confirmPaymentButton.setForeground(Colors.BUTTON_SUCCESS_TEXT); 
        confirmPaymentButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        confirmPaymentButton.setBounds(xMargin, yPos, fieldWidth + 100, 40); 
        detailsFormPanel.add(confirmPaymentButton); 
        yPos += 40 + 10;

        JButton cancelButton = new JButton("Batal"); 
        cancelButton.setBackground(Colors.BUTTON_DANGER_BACKGROUND); 
        cancelButton.setForeground(Colors.BUTTON_DANGER_TEXT); 
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
        cancelButton.setBounds(xMargin, yPos, (fieldWidth + 100) / 2 - 5, 30); 
        detailsFormPanel.add(cancelButton); 

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
            String shippingAddress = addressArea.getText();
            int userId = Main.currentUser != null ? Main.currentUser.getUserId() : -1;  
            String vaNumber = null;

            if (userId == -1) { 
                JOptionPane.showMessageDialog(this, "Error: User tidak teridentifikasi. Silakan login ulang.", "Error", JOptionPane.ERROR_MESSAGE); 
                return;
            }
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
            Date date = new Date(); 
            String orderDate = formatter.format(date); 
            String orderId = "ORD" + System.currentTimeMillis() / 1000; 


            if ("Virtual Account".equals(paymentMethod)) { 
                if (Main.currentUser == null || Main.currentUser.getPhoneNumber() == null || Main.currentUser.getPhoneNumber().isEmpty()) {  
                    JOptionPane.showMessageDialog(this, "Nomor telepon pengguna tidak ditemukan untuk Virtual Account.", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                vaNumber = "727" + Main.currentUser.getPhoneNumber(); 
                
                StringBuilder receiptMessage = new StringBuilder(); 
                receiptMessage.append("<html><body style='width: 350px; font-family: Monospace; font-size: 10pt; padding: 10px;'>"); 
                receiptMessage.append("<center><b>--- Struk Pembayaran CyclePro ---</b></center><br>"); 
                receiptMessage.append("------------------------------------------<br>"); 
                receiptMessage.append("No. Pesanan: ").append(orderId).append("<br>"); 
                receiptMessage.append("Tanggal    : ").append(orderDate).append("<br>"); 
                receiptMessage.append("Pelanggan  : ").append(Main.currentUser.getUsername()).append("<br>");  
                receiptMessage.append("------------------------------------------<br>"); 
                receiptMessage.append("<b>Produk:</b><br>"); 
                receiptMessage.append(String.format("%-30.30s Rp%,10.0f", selectedProduct.getName(), selectedProduct.getPrice())).append("<br>");  
                receiptMessage.append("------------------------------------------<br>"); 
                receiptMessage.append(String.format("%-30.30s <b>Rp%,10.0f</b>", "Total Harga:", selectedProduct.getPrice())).append("<br>"); 
                receiptMessage.append("------------------------------------------<br>"); 
                receiptMessage.append("Metode Bayar: ").append(paymentMethod).append("<br>"); 
                receiptMessage.append("<b>No. Virtual Account: ").append(vaNumber).append("</b><br>"); 
                receiptMessage.append("Kurir       : ").append(courier).append("<br>"); 
                receiptMessage.append("Alamat      : ").append(shippingAddress.replaceAll("\n", "<br>              ")).append("<br>"); 
                receiptMessage.append("------------------------------------------<br>"); 
                receiptMessage.append("<br><center><b>Silakan segera selesaikan pembayaran Anda.</b><br>"); 
                receiptMessage.append("Terima kasih telah berbelanja di CyclePro!</center><br>"); 
                receiptMessage.append("</body></html>"); 

                DatabaseHelper.createOrder(userId, selectedProduct.getProductId(), shippingAddress, courier, paymentMethod, selectedProduct.getPrice(), vaNumber);  
                
                JEditorPane editorPane = new JEditorPane("text/html", receiptMessage.toString()); 
                editorPane.setEditable(false); 
                editorPane.setBackground(Colors.BACKGROUND_SECONDARY); 
                JScrollPane receiptScrollPane = new JScrollPane(editorPane); 
                receiptScrollPane.setPreferredSize(new Dimension(480, 400)); 

                JOptionPane.showMessageDialog(this, receiptScrollPane, "Konfirmasi Pembayaran Virtual Account", JOptionPane.INFORMATION_MESSAGE); 

            } else { // COD
                DatabaseHelper.createOrder(userId, selectedProduct.getProductId(), shippingAddress, courier, paymentMethod, selectedProduct.getPrice(), null);  
                
                StringBuilder codReceiptMessage = new StringBuilder(); 
                codReceiptMessage.append("<html><body style='width: 300px; font-family: Monospace; font-size: 10pt; padding:10px;'>"); 
                codReceiptMessage.append("<center><b>--- Konfirmasi Pesanan CyclePro ---</b></center><br>"); 
                codReceiptMessage.append("-----------------------------------<br>"); 
                codReceiptMessage.append("No. Pesanan: ").append(orderId).append("<br>"); 
                codReceiptMessage.append("Tanggal    : ").append(orderDate).append("<br>"); 
                codReceiptMessage.append("Pelanggan  : ").append(Main.currentUser.getUsername()).append("<br>");  
                codReceiptMessage.append("-----------------------------------<br>"); 
                codReceiptMessage.append("<b>Produk:</b><br>"); 
                codReceiptMessage.append(String.format("%-20.20s Rp%,10.0f", selectedProduct.getName(), selectedProduct.getPrice())).append("<br>");  
                codReceiptMessage.append("-----------------------------------<br>"); 
                codReceiptMessage.append(String.format("%-20.20s <b>Rp%,10.0f</b>", "Total Bayar:", selectedProduct.getPrice())).append("<br>"); 
                codReceiptMessage.append("-----------------------------------<br>"); 
                codReceiptMessage.append("Metode Bayar: ").append(paymentMethod).append("<br>"); 
                codReceiptMessage.append("Kurir       : ").append(courier).append("<br>"); 
                codReceiptMessage.append("Alamat      : ").append(shippingAddress.replaceAll("\n", "<br>              ")).append("<br>"); 
                codReceiptMessage.append("-----------------------------------<br>"); 
                codReceiptMessage.append("<br><center><b>Pesanan Anda akan segera diproses.</b><br>"); 
                codReceiptMessage.append("Mohon siapkan pembayaran saat kurir tiba.</center><br>"); 
                codReceiptMessage.append("</body></html>"); 

                JEditorPane editorPaneCod = new JEditorPane("text/html", codReceiptMessage.toString()); 
                editorPaneCod.setEditable(false); 
                editorPaneCod.setBackground(Colors.BACKGROUND_SECONDARY); 
                JScrollPane scrollPaneCod = new JScrollPane(editorPaneCod); 
                scrollPaneCod.setPreferredSize(new Dimension(430, 350)); 

                JOptionPane.showMessageDialog(this, scrollPaneCod, "Konfirmasi Pesanan COD", JOptionPane.INFORMATION_MESSAGE); 
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