import java.awt.Color;

public class Colors {

    private Colors() {}

    // Warna Latar Belakang Utama Aplikasi
    public static final Color BACKGROUND_PRIMARY = new Color(0xF4F6F8); // Light Grayish Blue

    // Warna Latar Belakang Sekunder (misalnya untuk panel, kartu)
    public static final Color BACKGROUND_SECONDARY = Color.WHITE;

    // Warna untuk Komponen seperti Navbar atau Panel Header
    public static final Color NAVBAR_BACKGROUND = new Color(0x2C3E50); // Dark Slate Blue
    public static final Color NAVBAR_TEXT = Color.WHITE;

    // Warna untuk Tombol Utama (Primary Buttons)
    public static final Color BUTTON_PRIMARY_BACKGROUND = new Color(0x3498DB); // Peter River Blue
    public static final Color BUTTON_PRIMARY_TEXT = Color.WHITE;
    public static final Color BUTTON_PRIMARY_HOVER_BACKGROUND = new Color(0x2980B9); // Belize Hole (darker blue)

    // Warna untuk Tombol Sekunder (Secondary/Accent Buttons)
    public static final Color BUTTON_SECONDARY_BACKGROUND = new Color(0x95A5A6); // Concrete Gray
    public static final Color BUTTON_SECONDARY_TEXT = Color.WHITE;
    public static final Color BUTTON_SECONDARY_HOVER_BACKGROUND = new Color(0x7F8C8D); // Asbestos (darker gray)
    
    // Warna untuk Tombol Aksi Penting (misalnya "Daftar", "Simpan")
    public static final Color BUTTON_SUCCESS_BACKGROUND = new Color(0x2ECC71); // Emerald Green
    public static final Color BUTTON_SUCCESS_TEXT = Color.WHITE;
    public static final Color BUTTON_SUCCESS_HOVER_BACKGROUND = new Color(0x27AE60); // Nephritis (darker green)

    // Warna untuk Tombol Peringatan atau Batal
    public static final Color BUTTON_DANGER_BACKGROUND = new Color(0xE74C3C); // Alizarin Red
    public static final Color BUTTON_DANGER_TEXT = Color.WHITE;
    public static final Color BUTTON_DANGER_HOVER_BACKGROUND = new Color(0xC0392B); // Pomegranate (darker red)

    // Warna Teks Utama
    public static final Color TEXT_PRIMARY = new Color(0x333333); // Very Dark Gray (almost black)

    // Warna Teks Sekunder (misalnya untuk sub-judul, deskripsi)
    public static final Color TEXT_SECONDARY = new Color(0x777777); // Medium Gray

    // Warna untuk Border atau Garis Pemisah
    public static final Color BORDER_COLOR = new Color(0xBDC3C7); // Silver

    public static final Color TEXT_LINK = new Color(0x3498DB); // Peter River Blue

    // Contoh penggunaan:
    // myFrame.getContentPane().setBackground(Colors.BACKGROUND_PRIMARY);
    // myPanel.setBackground(Colors.BACKGROUND_SECONDARY);
    // myButton.setBackground(Colors.BUTTON_PRIMARY_BACKGROUND);
    // myButton.setForeground(Colors.BUTTON_PRIMARY_TEXT);
    // titleLabel.setForeground(Colors.TEXT_PRIMARY);
    // headerPanel.setBackground(Colors.NAVBAR_BACKGROUND);
}