import java.awt.Color;

public class Colors {

    private Colors() {}

    // Warna Latar Belakang Utama Aplikasi
    public static final Color BACKGROUND_PRIMARY = new Color(0xF4F6F8); // Light Grayish Blue

    // Warna Latar Belakang Sekunder (misalnya untuk panel, kartu)
    public static final Color BACKGROUND_SECONDARY = Color.WHITE;

    // Warna untuk Komponen seperti Navbar atau Panel Header
    public static final Color NAVBAR_BACKGROUND = new Color(0xD4AF37); // Sama dengan BUTTON_GOLD_BACKGROUND (warna hover)
    public static final Color NAVBAR_BACKGROUND_DARKER = new Color(0xB59874); // Disesuaikan agar tetap harmonis dengan warna emas
    public static final Color NAVBAR_TEXT = Color.WHITE;

    // Warna untuk Tombol Utama (Primary Buttons)
    public static final Color BUTTON_PRIMARY_BACKGROUND = new Color(0x3498DB); // Peter River Blue
    public static final Color BUTTON_PRIMARY_TEXT = Color.WHITE;
    public static final Color BUTTON_PRIMARY_HOVER_BACKGROUND = new Color(0x2980B9); // Belize Hole (darker blue)
    public static final Color BUTTON_GOLD_BACKGROUND = new Color(0xD4AF37); // Gold/Emas untuk tombol Login

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

    // Warna baru untuk gradasi dan background input field
    public static final Color BACKGROUND_LIGHT_GREY_GRADIENT = new Color(240, 240, 240); // Untuk gradasi panel registrasi
    public static final Color INPUT_FIELD_LIGHT_BACKGROUND = new Color(250, 250, 250); // Untuk background field
}